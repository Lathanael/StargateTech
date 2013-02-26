package lordfokas.stargatetech.networks.power;

import java.util.ArrayList;

import net.minecraft.block.Block;

import lordfokas.stargatetech.util.CoordinateSet;
import lordfokas.stargatetech.util.Helper;

public final class PowerNetStaticRouter {
	private static final PowerNetStaticRouter router = new PowerNetStaticRouter();
	private PowerNetStaticRouter(){}
	
	private class Result{
		public int power;
		public float fill;
		public CoordinateSet coords;
		public Result(int p, float f, CoordinateSet cs)
			{ power = p; fill = f; coords = cs; }
	}
	
	private class RoutingProcess{
		private ArrayList<Result> results = new ArrayList();
		private ArrayList<CoordinateSet> mem = new ArrayList();
		private int req;
		private boolean partial;
		private CoordinateSet start;
		private int found;
		
		public RoutingProcess(int r, CoordinateSet cs, boolean p){
			req = r; start = cs; found = 0; partial = p;
			process(cs);
		}
		
		public int consume(){
			if(found < req){
				if(partial){
					return found;
				}else{
					empty();
					return 0;
				}
			}
			int ret = 0;
			Result c = null;
			while(ret < req){
				c = new Result(-1, -1, null);
				for(Result i : results){
					if(c == null) c = i;
					else if(c.fill < i.fill) c = i;
				}
				ret += c.power;
				results.remove(c);
			}
			if(ret > req){
				CoordinateSet cs = c.coords;
				IPowerNetSource ins = (IPowerNetSource) Helper.getBlockInstance(cs);
				ins.giveBack(cs.w, cs.x, cs.y, cs.z, ret-req);
			}
			empty();
			return ret;
		}
		
		private void empty(){
			for(Result r : results){
				CoordinateSet cs = r.coords;
				IPowerNetSource ins = (IPowerNetSource) Helper.getBlockInstance(r.coords);
				ins.giveBack(cs.w, cs.x, cs.y, cs.z, r.power);
			}
		}

		private void process(CoordinateSet cs){
			for(int i = 0; i < 6; i++){
				CoordinateSet derivate = cs.fromDirection(i);
				Block block = Helper.getBlockInstance(derivate);
				if(!mem.contains(derivate)){
					if(block instanceof IPowerNetComponent){
						boolean fr = ((IPowerNetComponent)block).canConduitConnectOnSide(derivate.w, derivate.x, derivate.y, derivate.z, Helper.oppositeDirection(i));
						boolean bc = ((IPowerNetComponent)block).canConduitConnectOnSide(cs.w, cs.x, cs.y, cs.z, i);
						if(fr && bc){
							mem.add(derivate);
							IPowerNetComponent inc = (IPowerNetComponent) block;
							if(inc.getPowerComponentType() == IPowerNetComponent.EPowerComponentType.SOURCE){
								if(inc instanceof IPowerNetSource){
									int p = ((IPowerNetSource)inc).requestPower(derivate.w, derivate.x, derivate.y, derivate.z, req);
									if(p > 0){
										float f = ((IPowerNetSource)inc).getFill(derivate.w, derivate.x, derivate.y, derivate.z);
										results.add(new Result(p, f, derivate.clone()));
										found += p;
									}
								}
							}else if(inc.getPowerComponentType() == IPowerNetComponent.EPowerComponentType.CONDUIT){
								process(derivate);
							}
						}
					}
				}
			}
		}
	}
	
	public static int route(int request, CoordinateSet cs){
		return router.run(request, cs, false);
	}
	
	public static int route(int request, CoordinateSet cs, boolean p){
		return router.run(request, cs, p);
	}
	
	private int run(int request, CoordinateSet cs, boolean partial){
		return new RoutingProcess(request, cs, partial).consume();
	}
}

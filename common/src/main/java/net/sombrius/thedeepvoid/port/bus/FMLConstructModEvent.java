package net.sombrius.thedeepvoid.port.bus;

/** While the mod is constructed; the work runs right away (NeoForge: on the main thread through enqueueWork) */
public class FMLConstructModEvent extends Event {
	public void enqueueWork(Runnable work) {
		work.run();
	}
}

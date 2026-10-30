package net.sombrius.thedeepvoid.port.bus;

/** Client setup, after the registries are frozen; the work runs right away (NeoForge: on the main thread through enqueueWork) */
public class FMLClientSetupEvent extends Event {
	public void enqueueWork(Runnable work) {
		work.run();
	}
}

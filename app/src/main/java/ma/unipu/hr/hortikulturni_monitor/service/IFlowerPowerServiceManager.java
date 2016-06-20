package ma.unipu.hr.hortikulturni_monitor.service;



public interface IFlowerPowerServiceManager
{
	public void bind();
	public void unbind();
	
	public void connect();
	public void disconnect();
	public boolean isEnabled();
	public boolean isConnected();
	
	public void pause();
	
	public void enablePersistency(long period, int maxListSize, String storageLocation, String seriesId);
	public void disablePersistency();
	

	public void enableReconnect(long period);
	public void disableReconnect();

	
	public void addServiceListener(IFlowerPowerServiceListener listener);
	public void removeServiceListener(IFlowerPowerServiceListener listener);
	
	public void destroy();
}

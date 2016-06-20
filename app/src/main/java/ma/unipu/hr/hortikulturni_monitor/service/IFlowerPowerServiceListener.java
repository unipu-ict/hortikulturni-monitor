package ma.unipu.hr.hortikulturni_monitor.service;

import ma.unipu.hr.hortikulturni_monitor.model.FlowerPower;

public interface IFlowerPowerServiceListener
{

	public void serviceConnected();
	public void serviceDisconnected();
	public void serviceFailed(RuntimeException extra);
	
	public void deviceDiscovered(BluetoothDeviceModel extra);
	public void deviceConnected();
	public void deviceDisconnected();
	public void deviceReady(IFlowerPowerDevice device);
	public void dataAvailable(FlowerPower fp);
	
	
}

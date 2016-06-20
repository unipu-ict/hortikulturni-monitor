package ma.unipu.hr.hortikulturni_monitor.service;

public class BluetoothNotEnabledException extends RuntimeException
{

	public BluetoothNotEnabledException()
	{
		super();
	}
	
	public BluetoothNotEnabledException(String msg)
	{
		super(msg);
	}
	
}

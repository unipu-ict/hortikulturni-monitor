package ma.unipu.hr.hortikulturni_monitor.service;

public class BluetoothNotSupportedException extends RuntimeException
{

	public BluetoothNotSupportedException()
	{
		super();
	}
	
	public BluetoothNotSupportedException(String msg)
	{
		super(msg);
	}
}

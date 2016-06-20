package ma.unipu.hr.hortikulturni_monitor.timeseries.dao;

import java.util.LinkedList;

public interface ITimeSeriesModelDAO
{
	public void load(LinkedList<Long> timestamps, LinkedList<Float> values) throws Exception;
	public void save(LinkedList<Long> timestamps, LinkedList<Float> values, boolean append) throws Exception;
	public void deleteDataFiles();
}

package com.softmasters.dawuro.utils;

public interface AsyncTaskCompleteListener<T> {

	public void launchTask();
	public void onTaskComplete(T result);
}

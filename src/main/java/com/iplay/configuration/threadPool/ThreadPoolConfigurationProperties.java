package com.iplay.configuration.threadPool;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iplay.thread-pool")
public class ThreadPoolConfigurationProperties {
	
	/**
	 * Default: availableProcessors()+1
	 */
	private int corePoolSize = Runtime.getRuntime().availableProcessors()+1;

	/**
	 * Default: availableProcessors()*2+1
	 */
	private int maxPoolSize = Runtime.getRuntime().availableProcessors()*2+1;

	/**
	 * Default: 60
	 */
	private int keepAliveSeconds = 60;

	/**
	 * Default: 100
	 */
	private int queueCapacity = 100;

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getKeepAliveSeconds() {
		return keepAliveSeconds;
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public int getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
}

package com.iplay.configuration.threadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableConfigurationProperties(ThreadPoolConfigurationProperties.class)
public class ThreadPoolConfig {
	@Autowired
	private ThreadPoolConfigurationProperties prop;

	@Bean
	@Primary
	public Executor poolExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(prop.getCorePoolSize());
		executor.setMaxPoolSize(prop.getMaxPoolSize());
		executor.setQueueCapacity(prop.getQueueCapacity());
		executor.setKeepAliveSeconds(prop.getKeepAliveSeconds());
		executor.setThreadNamePrefix("IplayExecutor-");
		// CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
	
	@Bean  
    public TaskScheduler poolScheduler() {  
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();  
        scheduler.setThreadNamePrefix("IplayScheduler-");
        scheduler.setPoolSize(prop.getCorePoolSize());  
        return scheduler;  
    }  
}

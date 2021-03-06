/*
 * Copyright 2016-2018 Nextop Co.,Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.nextop.lite.pool.util.concurrent.executor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RunnableFuture;

/**
 * @author Jingqi Xu
 */
public interface XExecutorService extends ExecutorService {
	
	/**
	 * 
	 */
	List<Listener> getListeners();
	
	boolean addListener(Listener listener);
	
	boolean delListener(Listener listener);
	
	void setListeners(List<Listener> listeners);
	
	/**
	 * 
	 */
	String getName();
	
	boolean isVerbose();
	
	BlockingQueue<?> getQueue();
	
	void setVerbose(boolean verbose);
	
	void allowCoreThreadTimeOut(boolean allow);
	
	/**
	 * 
	 */
	interface Listener {
		
		void onTerminated(XExecutorService executor);
		
		void prevEnqueue(XExecutorService executor, RunnableFuture<?> future);
		
		void prevExecute(XExecutorService executor, RunnableFuture<?> future);
		
		void postExecute(XExecutorService executor, RunnableFuture<?> future, Throwable tx);
	}
}

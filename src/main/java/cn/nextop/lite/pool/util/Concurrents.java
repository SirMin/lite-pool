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

package cn.nextop.lite.pool.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;

/**
 * @author Jingqi Xu
 */
public final class Concurrents {
	
	/**
	 * 
	 */
	public static long sub(long v1, long v2) {
		return max(max(v1, 0) - max(v2, 0), 0);
	}
	
	/**
	 * 
	 */
	public static long terminate(ExecutorService exec, long timeout, TimeUnit unit)
	throws InterruptedException {
		// Precondition checking
		if(exec == null) return timeout;
		if(!exec.isShutdown()) exec.shutdown();
		
		//
		if(timeout <= 0) {
			return 0;
		} else {
			final long now = System.nanoTime();
			exec.awaitTermination(timeout, unit);
			final long elapsedTime = System.nanoTime() - now;
			return sub(timeout, unit.convert(elapsedTime, TimeUnit.NANOSECONDS));
		}
	}
	
	public static long terminateQuietly(ExecutorService exec, long timeout, TimeUnit unit) {
		final long now = System.nanoTime();
		try {
			return terminate(exec, timeout, unit);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			final long elapsedTime = System.nanoTime() - now;
			return sub(timeout, unit.convert(elapsedTime, TimeUnit.NANOSECONDS));
		}
	}
}

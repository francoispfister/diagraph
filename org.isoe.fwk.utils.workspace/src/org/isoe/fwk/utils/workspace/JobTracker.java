/*
 * Copyright (c) 2006, 2010 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.isoe.fwk.utils.workspace;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

/**
 * Collects jobs being scheduled during some period of time and provides condition trigger
 * which signals when all the jobs scheduled have finished.
 * @author artem
 */
public class JobTracker extends JobChangeAdapter {
	private final boolean[] condition = new boolean[] {false};
	private final List<Job> knownJobs = Collections.synchronizedList(new LinkedList<Job>());
	private boolean stopCollectingJobs = false;

	public JobTracker() {
		fixCondition();
	}

	@Override
	public void scheduled(IJobChangeEvent event) {
		if (!stopCollectingJobs) {
			knownJobs.add(event.getJob());
			fixCondition();
		}
	}

	@Override
	public void done(IJobChangeEvent event) {
		if (knownJobs.remove(event.getJob())) {
			fixCondition();
		}
	}

	private void fixCondition() {
		condition[0] = knownJobs.size() != 0;
	}

	public int getJobsCount() {
		return knownJobs.size();
	}

	public boolean[] getNonEmptyCondition() {
		return condition;
	}

	public void start() {
		Job.getJobManager().addJobChangeListener(this);
	}

	/**
	 * Stop collecting jobs; still respects jobs done. 
	 */
	public void freeze() {
		stopCollectingJobs = true;
	}

	public void stop() {
		Job.getJobManager().removeJobChangeListener(this);
		knownJobs.clear();
	}

	public void dump() {
		Job[] copy = knownJobs.toArray(new Job[0]);
		for (Job j : copy) {
			Thread t = j.getThread();
			System.err.println(j.getClass().getName() + ":" + j.getName() + "; " + (t == null ? "unknown" : t.getName()));
		}
	}
}

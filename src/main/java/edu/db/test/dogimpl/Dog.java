package edu.db.test.dogimpl;

import java.util.concurrent.*;

public class Dog {

	private Leg left1;
	private Leg left2;
	private Leg right1;
	private Leg right2;

	public Dog(Leg left1, Leg left2, Leg right1, Leg right2) {
		this.left1 = left1;
		this.left2 = left2;
		this.right1 = right1;
		this.right2 = right2;
	}

	private ExecutorService executorService;
	private volatile Thread thread;

	public void start() {
		Thread thread;
		thread = new Thread(this::run);
		thread.start();
		this.thread = thread;
	}

	public void stop() {
		thread.interrupt();
		thread = null;
	}

	private void run() {
		executorService = Executors.newSingleThreadExecutor();
		Side side = Side.LEFT;
		try {
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					break;
				}
				switch (side) {
					case LEFT:
						runSide(left1, left2);
						break;
					case RIGHT:
						runSide(right1, right2);
						break;
				}
				side = side.opposite();
			}
		} catch (InterruptedException e) {
				/*continuing will interrupt*/
		} catch (ExecutionException e) {
			e.printStackTrace(); /*log and interrupt. there may be a restart*/
		} finally {
			//здесь можно было бы привести ноги в дефолтное состояние
			executorService.shutdown();
			executorService = null;
		}
	}

	private void runSide(Leg leg1, Leg leg2) throws InterruptedException, ExecutionException {
		Future<?> submit = executorService.submit(leg1::step);
		leg2.step();
		submit.get();
		Thread.sleep(1000);
	}

	enum Side {
		LEFT {
			@Override
			public Side opposite() {
				return RIGHT;
			}
		},

		RIGHT {
			@Override
			public Side opposite() {
				return LEFT;
			}
		};

		public abstract Side opposite();
	}

}

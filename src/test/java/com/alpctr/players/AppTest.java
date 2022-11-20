package com.alpctr.players;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alpctr.data.MessageData;
import com.alpctr.data.StartingData;
import com.alpctr.event.Executor;
import com.alpctr.member.Player;



public class AppTest 
{
	private DataBus bus;
	
	@Before
	public void setUp() {
		bus = DataBus.getInstance();
	}

	@Test
    public void sendMessageToEachOtherTest()
    {
    	CountDownLatch latch = new CountDownLatch(2);

		Player player1 = new Player("player1");
		Player player2 = new Player("player2");
		
		player1.setLatch(latch);
		player2.setLatch(latch);
		
        bus.subscribe(player1);
        bus.subscribe(player2);

        player1.send(MessageData.of("Hey there!"), bus);
        
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
        bus.unsubscribe(player1);
        bus.unsubscribe(player2);
        
		ExecutorService taskExecutor = Executor.getInstance().getExecutor();
		taskExecutor.shutdown();
        
        Assert.assertEquals(latch.getCount(), 0);

    }
    
	
	@Test
    public void sendStartingDataTest()
    {
    	CountDownLatch latch = new CountDownLatch(2);

		Player player1 = new Player("player1");
		Player player2 = new Player("player2");
		
		player1.setLatch(latch);
		player2.setLatch(latch);
		
        bus.subscribe(player1);
        bus.subscribe(player2);

        player1.send(StartingData.of(LocalDateTime.now(), "StartingData Test"), bus);
        
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
        bus.unsubscribe(player1);
        bus.unsubscribe(player2);
        
		ExecutorService taskExecutor = Executor.getInstance().getExecutor();
		taskExecutor.shutdown();
        
        Assert.assertEquals(latch.getCount(), 0);

    }
    
    

    
}
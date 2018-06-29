import java.util.*;

public class TestInterface 
{
	public static void main(String args[]) throws InterruptedException
	{
		PlayBallInterface paly_ball_interface = new PlayBallInterface();
		paly_ball_interface.start();
				
//		while(true)
//		{
//			paly_ball_interface.sleep(10);
//    	System.out.println(paly_ball_interface.Registration("lalb", "12345", "12345", "male", "born where", "shanghai"));
		System.out.println(paly_ball_interface.ChangeFriend("abc", "小明", "", "ss"));
//		System.out.println(paly_ball_interface.Login("小明","1234"));
//		System.out.println(paly_ball_interface.IsMailChanged("abc","bot1"));
//		System.out.println(paly_ball_interface.AddFriendFeedback("abc", "bot1", "lala",0));
//		System.out.println(paly_ball_interface.GetFriendsLists("abc", "小明"));
//		System.out.println(paly_ball_interface.GetFriendsLists("abc", "lala"));
//    	System.out.println(paly_ball_interface.RecoverPosswordStepTwo("shanghai", "15264093620"));
//		}
	}
}

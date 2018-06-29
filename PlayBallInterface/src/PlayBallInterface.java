import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

class PlayBallInterface extends Thread
{
	InetAddress IP;
	int port;
	DatagramSocket socket;
	boolean receive_flag = false;
	boolean send_flag = false;
	String global_send_msg;
	String global_receive_msg;
	
	public void run()
	{
		//初始化socket
		try
		{
			IP = InetAddress.getByName("10.161.24.213");
		}
		catch(UnknownHostException e)
		{}
		
		try
		{
			socket = new DatagramSocket();
			socket.setSoTimeout(0);
		}
		catch(SocketException e)
		{}		
		port = 9999;
		global_send_msg = new String();
		global_receive_msg = new String();
		
		while(true)
		{		
			try 
			{
				sleep(1);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			
			//发送消息
			if(send_flag)
			{				
				System.out.println("sending");
				byte[] msg_send = global_send_msg.getBytes();
				System.out.println(global_send_msg);
				DatagramPacket packet_send = new DatagramPacket(msg_send,msg_send.length,IP,port);
				try
				{
					socket.send(packet_send);
				}
				catch(IOException e)
				{

				}			
			
				//接收消息
				byte[] msg_receive = new byte[1024];
				DatagramPacket packet_receive = new DatagramPacket(msg_receive,msg_receive.length);
				try
				{
					socket.receive(packet_receive);
				}
				catch(IOException e)
				{

				}				
				global_receive_msg = new String(msg_receive,0,packet_receive.getLength());				
				receive_flag = true;
				send_flag = false;
			}
		}
	}

	public String SendAndReceive(String send_msg)
	{		
		global_send_msg = send_msg;
		send_flag = true;
		
		while(true)
		{
			try 
			{
				sleep(1);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			if(receive_flag == true)
			{		
				System.out.println("receiveing");
				receive_flag = false;
				break;
			}
		}		
		return global_receive_msg;
	}
	
	//查询邮箱
	public String IsMailChanged(String public_key, String name)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "is_mail_changed");
		msg.put("data", data_string);
		msg.put("return", "");		 
			
		return SendAndReceive(msg.toString());		 
	}
	
	
	//登陆
	public String Login(String name, String password)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("password", password);
		String data_string = data.toString();
		
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", "");
		msg.put("type", "login");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//注册
	public String Registration(String name, String password, String phone_number, String gender, String private_question, String private_answer)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("password", password);
		data.put("phone_number", phone_number);
		data.put("gender", gender);
		data.put("private_question", private_question);
		data.put("private_answer", private_answer);
		
		String data_string = data.toString();		
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", "");
		msg.put("type", "registration");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());		
	}
	
	//找回密码 第一步
	public String RecoverPosswordStepOne(String phone_number)
	{
		JSONObject data = new JSONObject();		
		data.put("phone_number", phone_number);
		String data_string = data.toString();		
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", "");
		msg.put("type", "recover_password_step_one");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}	
	
	//找回密码 第二步
	public String RecoverPosswordStepTwo(String private_answer, String phone_number)
	{
		JSONObject data = new JSONObject();		
		data.put("private_answer", private_answer);
		data.put("phone_number", phone_number);
		String data_string = data.toString();		
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", "");
		msg.put("type", "recover_password_step_two");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//查询个人信息
	public String GetPersonalInfo(String public_key,  String name)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "recover_password_step_two");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//设置个人信息
	public String SetPersonalInfo(String public_key, String name, String phone_number, String gender, String college, String grade, String introduction)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("phone_number", phone_number);
		data.put("gender", gender);
		data.put("college", college);
		data.put("grade", grade);
		data.put("introduction", introduction);
		
		String data_string = data.toString();		
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "set_personal_info");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());		
	}
	
	//查询好友列表
	public String GetFriendsLists(String public_key, String name)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "get_friends_lists");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//添加好友
	public String ChangeFriend(String public_key, String name, String remove_friend, String add_friend)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("remove_friend", remove_friend);
		data.put("add_friend", add_friend);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "change_friend");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//添加好友反馈
	public String AddFriendFeedback(String public_key, String name, String new_friend,int return_result)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("new_friend", new_friend);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "add_friend_feedback");
		msg.put("data", data_string);
		msg.put("return", return_result);
		
		return SendAndReceive(msg.toString());
	}
	
	//查看好友信息
	public String GetFriendInfo(String public_key, String name, String friend_name)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("friend_name", friend_name);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "get_friend_info");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//查询可用场地
	public String SeeSite(String public_key, String name, String site_name, String date)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("site_name", site_name);
		data.put("date", date);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "see_site");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//申请比赛
	public String ApplyGame(String public_key, String name, String sit_name, int[] times, String date)
	{
		JSONObject data = new JSONObject();
		data.put("name", name);
		data.put("site_name", sit_name);
		data.put("times", times);
		data.put("date", date);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "apply_game");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//邀请好友入队
	public String InviteInfo(String public_key, String name, String game_id, String player_name)
	{
		JSONObject data = new JSONObject();	
		data.put("name", name);
		data.put("game_id", game_id);
		data.put("player_name", player_name);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "invite_info");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//回复邀请
	public String InviteFeedback(String public_key, String msgs, String name, String game_id)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("msgs", msgs);
		data.put("game_id", game_id);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "invite_feedback");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}	
	
	//申请匹配
	public String ApplyMatch(String public_key, String game_id, String name)
	{
		JSONObject data = new JSONObject();	
		data.put("name", name);
		data.put("game_id", game_id);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "apply_match");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}	
	
	//退出比赛
	public String ExitGame(String public_key, String game_id, String msgs,  String name)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("game_id", game_id);
		data.put("msg", msgs);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "exit_game");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//查看匹配详情
	public String MatchDetail(String public_key, String name, String game_id)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("final_id", game_id);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "match_detail");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//查看匹配结果
	public String MatchResult(String public_key, String name, String final_id)
	{
		JSONObject data = new JSONObject();		
		data.put("name", name);
		data.put("game_id", final_id);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "match_result");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}	
	
	//创建公告
	public String ApplyNotice(String public_key, String contents,  String name)
	{
		JSONObject data = new JSONObject();	
		data.put("name", name);
		data.put("contents", contents);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "apply_notice");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}
	
	//请求公告
	public String RequestNotice(String public_key,  String name)
	{
		JSONObject data = new JSONObject();	
		data.put("name", name);
		String data_string = data.toString();
		
		JSONObject msg = new JSONObject();
		msg.put("public_key", public_key);
		msg.put("type", "request_notice");
		msg.put("data", data_string);
		msg.put("return", "");
		
		return SendAndReceive(msg.toString());
	}

}




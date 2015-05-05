package br.com.rankmatch.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Match {

	private int id;
	private int state;
	private Date initTime;
	private Date finishTime;
	List<Player> listPlayer = new ArrayList<Player>();

	
	Match(int id)
	{
		setId(id);
	}
	
	public Match(String id) {
		this.id = Integer.parseInt(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getInitTime() {
		return initTime;
	}

	public void setInitTime(Date initTime) {
		this.initTime = initTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public List<Player> getListPlayer() {
		return listPlayer;
	}

	public void setListPlayer(List<Player> listPlayer) {
		this.listPlayer = listPlayer;
	}

}

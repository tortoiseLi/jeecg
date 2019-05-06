package org.jeecgframework.web.system.core.manager;

import java.util.Comparator;

import org.jeecgframework.web.system.core.common.entity.Client;

public class ClientSort implements Comparator<Client> {

	
	public int compare(Client prev, Client now) {
		return (int) (now.getLogindatetime().getTime()-prev.getLogindatetime().getTime());
	}

}

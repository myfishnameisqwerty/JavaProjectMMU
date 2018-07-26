package main.java.com.hit.controller;

import java.util.Observable;

import javax.swing.JOptionPane;

import main.java.com.hit.model.*;
import main.java.com.hit.view.*;

public class CacheUnitController implements Controller {

	private View view = null;

	private Model model = null;

	public CacheUnitController(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void update(Observable o, Object e) {

		if (o instanceof Model) {
			checkModelNotifierParam((String)e);
			
		} else if (o instanceof View) {
			checkViewNotifierParam((String)e);
		}
	}
	
	private void checkViewNotifierParam(String param)
	{
		switch(param)
		{
		case "CREATE":
			((CacheUnitModel) model).updateModelData("ALGO");
			((CacheUnitModel) model).updateModelData("CAPACITY");
			break;
		case "STATISTICS":
			((CacheUnitModel) model).updateModelData("SWAPS");
			((CacheUnitModel) model).updateModelData("REQUESTS");
			((CacheUnitModel) model).updateModelData("DMS");
			break;
		case "LOAD":
			String content = ((CacheUnitModel) model).loadFile();
			String response = ((CacheUnitModel) model).sendJsonString(content);
			JOptionPane.showMessageDialog(null, response, "Load Status", 0);
			break;
			
			
		}
	}
	
	private void checkModelNotifierParam(String param)
	{
		CacheUnitModel modelTemp = ((CacheUnitModel) model);
		switch(param)
		{
		case "ALGO":
			((CacheUnitView) view).setAlgoLbl(modelTemp.getAlgo());
			break;
		case "CAPACITY":
			((CacheUnitView) view).setCapacityLbl(modelTemp.getCapacity());
			break;
		case "SWAPS":
			((CacheUnitView) view).setSwaps(modelTemp.getTotalSwaps());
			break;
		case "REQUESTS":
			((CacheUnitView) view).setRequests(modelTemp.getTotalReq());
			break;
		case "DMS":
			((CacheUnitView) view).setDms(modelTemp.getTotalDMs());
			break;
		}
	}
}
package main.java.com.hit.view;

import java.util.Observable;

public class CacheUnitView extends Observable implements View {

	public CacheUnitPanel MainPanel;

	public CacheUnitView() {
		MainPanel = new CacheUnitPanel(this);
		
	}

	@Override
	public void start() {
		MainPanel.createAndShowGUI();
		MainPanel.setVisible();
		setChanged();
		updateUIData("CREATE");
	}

	@Override
	public <T> void updateUIData(T t) {
		setChanged();
		notifyObservers((String)t);
	}

	public void setCapacityLbl(String text) {
		MainPanel.getLblCapacityResp().setText(text);
	}

	public void setAlgoLbl(String text) {
		MainPanel.getLblAlgoResp().setText(text);
	}
	
	public void setSwaps(String swaps)
	{
		MainPanel.getLblNumOfSwapResp().setText(swaps);
	}
	
	public void setRequests(String reqs)
	{
		MainPanel.getLblNumOfReqResp().setText(reqs);
	}
	
	public void setDms(String dms)
	{
		MainPanel.getLblNumOfDMResp().setText(dms);
	}

}

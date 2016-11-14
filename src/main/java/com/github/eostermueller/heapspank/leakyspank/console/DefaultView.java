package com.github.eostermueller.heapspank.leakyspank.console;

import java.text.NumberFormat;
import java.util.Queue;

import com.github.eostermueller.heapspank.leakyspank.LeakySpankContext;
import com.github.eostermueller.heapspank.leakyspank.LeakySpankContext.LeakResult;
import com.github.eostermueller.heapspank.leakyspank.Model;

public class DefaultView implements ConsoleView {
	public DefaultView() {
		this.theLeakiest = new TheLeakiest(10);
	}
	LeakySpankContext leakySpankContext = null;
	TheLeakiest theLeakiest = null;
	private String multiLineDisplayData;
	private DisplayUpdateListener displayUpdateListener;

	private static final String DATA_LINE_FORMAT = 
			  "%4.1f%%"			//result.getPercentageOfRunsWithUpwardByteTrend() * 100,
			 +"  %4d"				//result.countRunsWithBytesIncrease,
			 +"  %4d"				//this.getLeakySpankContext().getCurrentRunCount(),
			 +"  %4d"				//result.countRunsWithInstanceCountIncrease,
			 +"  %4d"				//result.countRunsWithLeakierRank,
			 +"  %12s"				//result.line.bytes,
			 +"  %9s"				//result.line.instances,
			 +"  %7d"				//result.line.num,
			 +"  %s"				//result.line.className
	 		 +"%n";	
	private static final String HEADER_FORMAT = 
			  "%5s"			//result.getPercentageOfRunsWithUpwardByteTrend() * 100,
			 +"  %4s"				//result.countRunsWithBytesIncrease,
			 +" %4s"				//this.getLeakySpankContext().getCurrentRunCount(),
			 +" %4s"				//result.countRunsWithInstanceCountIncrease,
			 +" %4s"				//result.countRunsWithLeakierRank,
			 +" %9s"				//result.line.bytes,
			 +"    %8s"				//result.line.instances,
			 +" %7s"				//result.line.num,
			 +" %s"				//result.line.className
	 		 +"%n";	
	static final String LEAKY_SPANK = "leakySpank: ";
			
	
	@Override
	public void printView() throws Exception {
		
		LeakySpankContext ctx = this.getLeakySpankContext();
		
		displayHeader();
		
		if (ctx.getCurrentRunCount() % ctx.getRunCountPerWindow()==0) {
			this.theLeakiest.addLeakResults(ctx.getTopResults());
			
			StringBuilder sb = new StringBuilder();
			for (LeakResult result : this.theLeakiest.getTopResults() ) {
				sb.append( displayOneLine(result) );
			}
			this.setMultiLineDisplayData(sb.toString());
			this.displayUpdateListener.updated();
		}
		
		System.out.print(this.getMultiLineDisplayData());
		
		
	}
	private void setMultiLineDisplayData(String v) {
		this.multiLineDisplayData = v;
	}
	private String getMultiLineDisplayData() {
		return this.multiLineDisplayData;
	}
	private void displayHeader() {
		
		System.out.format(this.getFormatForHeader(), 
				"LKY%",
				"(B-INC",
				"(JMH",
				"(I-INC",
				"(R-INC",
				"BYTES",
				"INSTANCES",
				"NUM",
				"CLASS"
				);
	}

	protected String displayOneLine(LeakResult result) {
		return String.format(
				this.getFormatForDataLine(),
				result.getPercentageOfRunsWithUpwardByteTrend() * 100,
				result.countRunsWithBytesIncrease,
				this.getLeakySpankContext().getCurrentRunCount(),
				result.countRunsWithInstanceCountIncrease,
				result.countRunsWithLeakierRank,
				NumberFormat.getIntegerInstance().format(result.line.bytes),
				NumberFormat.getIntegerInstance().format(result.line.instances),
				result.line.num,
				result.line.className
				);
	}
	protected String getDisplayClassName(String fullPackageAndClassDotSeparated) {
		return fullPackageAndClassDotSeparated;
	}
	protected String getFormatForDataLine() {
		return DefaultView.DATA_LINE_FORMAT;
	}

	@Override
	public boolean shouldExit() {
		return false;
	}

	private String getFormatForHeader() {
		return DefaultView.HEADER_FORMAT;
	}

	@Override
	public void sleep(long millis) throws Exception {
		Thread.sleep(millis);
	}

	private void debug(String msg) {
		System.out.println(DefaultView.LEAKY_SPANK+msg);
	}
	@Override
	public void setLeakySpankContext(LeakySpankContext ctx) {
		this.leakySpankContext  = ctx;
	}
	@Override
	public LeakySpankContext getLeakySpankContext() {
		return this.leakySpankContext;
	}
	@Override
	public void setDisplayUpdateListener(
			DisplayUpdateListener v) {
		this.displayUpdateListener = v;
	}
	@Override 
	public DisplayUpdateListener getDisplayUpdateListener() {
		return this.displayUpdateListener;
	}
}

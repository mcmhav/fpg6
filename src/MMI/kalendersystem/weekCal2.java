package kalendersystem;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import objectTypes.*;
import objectTypes.Event;

import cell.*;


/**
 * @version 1.0 11/26/98
 */
public class weekCal2 extends JPanel {

	 private String[] days = { "Mandag ", "Tirsdag ", "Onsdag ", "Torsdag ", "Fredag ", "L\u00F8rdag ", "S\u00F8ndag " };
	 private String[] names = new String[7];
	 private String[][] data = new String[24][7];
	 private String[] dates = new String[7];
	 private int[] weekdays = new int[7];
//	 private int[] weekdays = {28,29,30,1,2,3,4};
//	 private int[] weekdays = {5,6,7,8,9,10,11};
	 
	 private String[] daysdouble = { "Mandag ", "", "Tirsdag ","", "Onsdag ","", "Torsdag ","", "Fredag ","", "L\u00F8rdag ","", "S\u00F8ndag ","" };
	 private String[] namesd = new String[14];
	 private String[][] datadouble = new String[24][14];
	 private String[] datesdouble = new String[14];
	 private int[] weekdaysd = new int[7];
	 private int[] monthsInWeek = new int[7];
	 
	 private int we,mo,sd,ed,cd,yr,pyr,spyr,cx,cys,cye,cw,chw,cx2,mu;
	 private int[] rows;
	 private Calendar calendar;
	 private MultiSpanCellTable week;
	 private MultiSpanCellTable time;
	 private JScrollPane scroll;
	 private JScrollPane scroll2;
	 private AttributiveCellTableModel weekModel;
	 final private CellSpan cellAtt;
	 private AttributiveCellTableModel timeModel;
	 private String[][] tidData = new String[24][1];
	 private String[] tidName = {"Time"};
	 private JPanel box;
	 public int q;
	 private PropertyChangeSupport pcs;
	 private ArrayList<objectTypes.Event> eve = new ArrayList<objectTypes.Event>();
	 private ArrayList<objectTypes.Event> eve2 = new ArrayList<objectTypes.Event>();
	 private int[] filltime, filltime2;
	 
	 public int getcx(){
		 return cx;
	 }
	 
	 public int[] getRows(){
		 return rows;
	 }
	 
	 public CellSpan getCellAtt(){
		 return cellAtt;
	 }
	 
	 public int getCurrentWeek(){
		 return cw;
	 }
	 
	 public int getChoosenWeekReal(){
		 return chw+cw;
	 }
	 
	 public int getChoosenWeek(){
		 int temp;
		 if(chw<1){
			  temp = chw + 52;
		  }else if(cw>52){
			  temp = chw - 52;
	  	  }
		 return chw;
	 }
	 
	 public String[] getDates(){
		 return dates;
	 }
	 
	 public int getStartDate(){
		 return sd;
	 }
	 
	 public int getEndDate(){
		 return ed;
	 }
	 
	 public int getCurrentDate(){
		 return cd;
	 }
	 
	 public int getChosenDate(){
		 return cx;
	 }
	 
	 public MultiSpanCellTable getWeek(){
		 return week;
	 }
	 
	 public int getStartTime(){
		 return cys;
	 }
	 
	 public int getEndTime(){
		 return cye;
	 }
	 
	 public String[] getDatesd(){
		 return datesdouble;
	 }
	 
	 public boolean isTwo(){
		 if(mu!=1){
			 return true;
		 }
		 return false;
	 }
	 
	 public weekCal2(int w) {
		 super( );

		 calendar = new GregorianCalendar();
		 calendar.setFirstDayOfWeek(Calendar.MONDAY);
		 mu=1;
		 initcal(w);
		 weekModel = new AttributiveCellTableModel(data,names);
		 cellAtt =(CellSpan)weekModel.getCellAttribute();
		 week = new MultiSpanCellTable( weekModel );
		 week.addMouseListener(new ms());
		 
		 scroll = new JScrollPane( week );
		 timeModel = new AttributiveCellTableModel(tidData,tidName);
		 time = new MultiSpanCellTable(timeModel);
		 scroll2 = new JScrollPane( time );
		 time.setEnabled(false);
		 time.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
//		 week.setDefaultRenderer(Object.class, new AttributiveCellRenderer2());
		 week.setEditingColumn(0);
		 week.setEditingRow(0);
		 pcs = new PropertyChangeSupport(this);
    
		 weekModel.addTableModelListener(new TML());
		 week.getSelectionModel().addListSelectionListener(new LSL());
		 week.getColumnModel().getSelectionModel().addListSelectionListener(new LSL());

		 JButton b_one   = new JButton("Combine");
		 b_one.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 int[] columns = {week.getSelectedColumn()};
				 rows    = week.getSelectedRows();
				 cellAtt.combine(rows,columns);
				 week.clearSelection();
				 week.revalidate();
				 week.repaint();
			 }
		 });
		 JButton b_split = new JButton("Split");
		 b_split.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 int column = week.getSelectedColumn();
				 int row    = week.getSelectedRow();
				 cellAtt.split(row,column);
				 week.clearSelection();
				 week.revalidate();
				 week.repaint();
			 }
		 });
		 
//		 int[] z = {1};
//		 int[] x = {2,3,4,5,6};
//		 
//		 cellAtt.combine(x, z);
//		 week.clearSelection();
//		 week.revalidate();
//		 week.repaint();
		 
		 JPanel p_buttons = new JPanel();
		 p_buttons.setLayout(new GridLayout(3,1));
		 p_buttons.add(b_one);
		 p_buttons.add(b_split);
	
		 box = new JPanel(new GridBagLayout());
		 GridBagConstraints c = new GridBagConstraints();
		 c.fill = c.BOTH;
		 scroll2.setPreferredSize(new Dimension(42,430));
		 scroll.setPreferredSize(new Dimension(950, 430));
		 box.add(scroll2,c);
		 box.add(scroll,c);
//		 box.add(p_buttons,c);
		 add( box );
//		 setSize( 1200, 430 );
	    
	    
		 setVisible(true);
    
	 }
  
	 public weekCal2(int w, int a){
		 super( );

		 calendar = new GregorianCalendar();
		 calendar.setFirstDayOfWeek(Calendar.MONDAY);
		 mu = a;
		 initcal(w);
    
    
		 weekModel = new AttributiveCellTableModel(datadouble,daysdouble);
		 cellAtt =(CellSpan)weekModel.getCellAttribute();
		 week = new MultiSpanCellTable( weekModel );
		 week.addMouseListener(new ms());
		 
		 scroll = new JScrollPane( week );
		 timeModel = new AttributiveCellTableModel(tidData,tidName);
		 time = new MultiSpanCellTable(timeModel);
		 scroll2 = new JScrollPane( time );
		 time.setEnabled(false);
		 time.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
//		 week.setDefaultRenderer(Object.class, new AttributiveCellRenderer2());
		 week.setEditingColumn(0);
		 week.setEditingRow(0);
		 pcs = new PropertyChangeSupport(this);
    
		 weekModel.addTableModelListener(new TML());
		 week.getSelectionModel().addListSelectionListener(new LSL());
		 week.getColumnModel().getSelectionModel().addListSelectionListener(new LSL());

		 
		 
		 JButton b_one   = new JButton("Combine");
		 b_one.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 int[] columns = {week.getSelectedColumn()};
				 rows    = week.getSelectedRows();
				 cellAtt.combine(rows,columns);
				 week.clearSelection();
				 week.revalidate();
				 week.repaint();
			 }
		 });
		 JButton b_split = new JButton("Split");
		 b_split.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 int column = week.getSelectedColumn();
				 int row    = week.getSelectedRow();
				 cellAtt.split(row,column);
				 week.clearSelection();
				 week.revalidate();
				 week.repaint();
			 }
		 });
		 
//		 int[] z = {1};
//		 int[] x = {2,3,4,5,6};
//		 
//		 cellAtt.combine(x, z);
//		 week.clearSelection();
//		 week.revalidate();
//		 week.repaint();
		 
		 JPanel p_buttons = new JPanel();
		 p_buttons.setLayout(new GridLayout(3,1));
		 p_buttons.add(b_one);
		 p_buttons.add(b_split);
	
		 box = new JPanel(new GridBagLayout());
		 GridBagConstraints c = new GridBagConstraints();
		 c.fill = c.BOTH;
		 scroll2.setPreferredSize(new Dimension(42,430));
		 scroll.setPreferredSize(new Dimension(950, 430));
		 box.add(scroll2,c);
		 box.add(scroll,c);
//		 box.add(p_buttons,c);
		 add( box );
//		 setSize( 1200, 430 );
	    
	    
		 setVisible(true);
	 }
	 
	 public void two(UserEvents ue){
		 
	 }
	 
	  private void get(){
	//	  cx2 = week.getSelectedColumns()[week.getSelectedColumns().length-1];
		  cys = week.getSelectedRow();
		  cx = week.getSelectedColumn();
		  rows = week.getSelectedRows();
		  if(cys>=0){
			  cye=week.getSelectedRows()[week.getSelectedRows().length-1]+1;
//			  if(cx2>cx&&(cye-cys!=1)){
//				  cx=cx2;
//			  }
			  cx2=cx;
			  week.setColumnSelectionInterval(cx, cx);
//			  System.out.println(cx + " " +cx2+" "+ cys+"-"+(cye));
//			  System.out.println("Date: "+dates[cx]);
		  }
		  
	  }
	  
	  private void get2(){
		  cys = week.getSelectedRow();
		  cx = week.getSelectedColumn();
		  rows = week.getSelectedRows();
		  if(cys>=0){
			  cye=week.getSelectedRows()[week.getSelectedRows().length-1]+1;
//			  if(cx2>cx&&(cye-cys!=1)){
//				  cx=cx2;
//			  }
			  cx2=cx;
//			  if((cx%2)==0){
//				  week.setColumnSelectionInterval(cx, cx+1);
//			  }else{
//				  week.setColumnSelectionInterval(cx, cx-1);
//			  }
			  week.setColumnSelectionInterval(cx, cx);
			  System.out.println(cx + " " +cx2+" "+ cys+"-"+(cye));
			  System.out.println("Date: "+datesdouble[cx]);

		  }
	  }
	  
	  private class LSL implements ListSelectionListener{
	
		  @Override
		  public void valueChanged(ListSelectionEvent e) {
			  if(mu!=1){
				  get2();
			  }else{
				  get();
			  }
		  }
		  
	  }
	  
	  private class TML implements TableModelListener{
	
			@Override
			public void tableChanged(TableModelEvent e) {
				
			}
		}
	  
	  private void initcal(int w){
		  for (int i = 0; i < 24; i++) {
				if(i<10){
					tidData[i][0] = "0"+i+":00";
				}else {
					tidData[i][0] = i+":00";
				}
			}
		    
		  cw = calendar.get(Calendar.WEEK_OF_YEAR);
		  yr = calendar.get(Calendar.YEAR);
		  pyr = yr;
		  
		  
		  
//		  if(cw+w<1){
//			  chw = w+52;
//			  yr--;
//		  }else if(cw+w>52){
//			  chw = w-52;
//			  yr++;
//	  	  }else{
//	  		  chw = w;
//		  }
	  		  chw = w;

		  weekDates(w);
	  }
	  
	  public void weekDates(int b){
		  
			we = b;
			cd = calendar.get(Calendar.DATE)+we*7;
			sd = cd - calendar.get(Calendar.DAY_OF_WEEK)+2;
			ed = sd +6;
			mo = calendar.get(Calendar.MONTH);
			
			if(sd>=28){
				int a=28;
				while(sd>a){
					mo++;
					if(mo==1&&(yr%4)==0){
						a= 29;
						sd -=29;
						ed -= 29;
					}else if(mo==1){
						a= 28;
						sd -=28;
						ed -= 28;
					}else if((mo==0||mo==2||mo==4||mo==6||mo==7||mo==9||mo==11)){
						a= 30;
						sd -=30;
						ed -= 30;
					}else if((mo==3||mo==5||mo==8||mo==10)){
						a= 31;
						sd -=31;
						ed -= 31;
					}
					if(mo>=12){
						yr++;
						mo=-1;
					}
				}
				if(mo==1&&(yr%4)==0&&(29-sd)<7){
					ed -= 29;
				}else if(mo==1&&(28-sd)<7){
					ed -= 28;
				}else if((mo==0||mo==2||mo==4||mo==6||mo==7||mo==9||mo==11)&&(31-sd)<7){
					ed -= 31;
				}else if((mo==3||mo==5||mo==8||mo==10||mo==12)&&(30-sd)<7){
					ed -= 30;;
				}
			}
			if(sd<1){
				while(sd<1){
						mo--;
						if(mo==1&&(yr%4)==0){
							sd += 29;
						}else if(mo==1){
							sd += 28;
						}else if(mo==0||mo==2||mo==4||mo==6||mo==7||mo==9||mo==11){
							sd += 31;
						}else if(mo==3||mo==5||mo==8||mo==10){
							sd += 30;
						}
					if(mo<0){
						yr--;
						mo=12;
					}
					ed = sd +6;
				}
				
				if(mo==1&&(yr%4)==0&&(29-sd)<7){
					ed -= 29;
				}else if(mo==1&&(28-sd)<7){
					ed -= 28;
				}else if((mo==0||mo==2||mo==4||mo==6||mo==7||mo==9||mo==11)&&(31-sd)<7){
					ed -= 31;
				}else if((mo==3||mo==5||mo==8||mo==10||mo==12)&&(30-sd)<7){
					ed -= 30;;
				}
			}
			if(sd-ed>0){
				if(yr!=pyr){
					for(int i = 6; i>=(7-ed); i--){
						if(mo+2==13){
							weekdays[i] = (ed+i-6);
							dates[i] = (ed+i-6)+"/" +(1)+"/"+pyr ;
							monthsInWeek[i]=1;
							daysdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)] = datesdouble[(i*2)+1];
							names[i] = days[i]+dates[i];
						}else{
							weekdays[i] = (ed+i-6);
							dates[i] = (ed+i-6)+"/" +(mo+2)+"/"+pyr ;
							monthsInWeek[i]=mo+1;
							daysdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)] = datesdouble[(i*2)+1];
							names[i] = days[i]+dates[i];
						}
					}
					for (int i = 0; i < (7-ed); i++) {
						weekdays[i] = (sd+i);
						dates[i] = (sd+i)+"/" + (mo+1)+"/"+yr;
						monthsInWeek[i]=mo;
						daysdouble[(i*2)+1] = dates[i];
						datesdouble[(i*2)+1] = dates[i];
						datesdouble[(i*2)] = datesdouble[(i*2)+1];
						names[i] = days[i]+dates[i];
					}
				}else{
					for(int i = 6; i>=(7-ed); i--){
						if(mo+2==13){
							weekdays[i] = (ed+i-6);
							dates[i] = (ed+i-6)+"/" +(1)+"/"+yr ;
							monthsInWeek[i]=1;
							daysdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)] = datesdouble[(i*2)+1];
							names[i] = days[i]+dates[i];
						}else{
							weekdays[i] = (ed+i-6);
							dates[i] = (ed+i-6)+"/" +(mo+2)+"/"+yr ;
							monthsInWeek[i]=mo+1;
							daysdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)+1] = dates[i];
							datesdouble[(i*2)] = datesdouble[(i*2)+1];
							names[i] = days[i]+dates[i];
						}
					}
					for (int i = 0; i < (7-ed); i++) {
						weekdays[i] = (sd+i);
						dates[i] = (sd+i)+"/" + (mo+1)+"/"+yr;
						monthsInWeek[i]=mo;
						daysdouble[(i*2)+1] = dates[i];
						datesdouble[(i*2)+1] = dates[i];
						datesdouble[(i*2)] = datesdouble[(i*2)+1];
						names[i] = days[i]+dates[i];
					}
				}
			}
			else{
				for (int i = 0; i < names.length; i++) {
					weekdays[i] = (sd+i);
					dates[i] = (sd+i)+"/" + (mo+1)+"/"+yr;
					monthsInWeek[i]=mo+1;
					daysdouble[(i*2)+1] = dates[i];
					datesdouble[(i*2)+1] = dates[i];
					datesdouble[(i*2)] = datesdouble[(i*2)+1];
					names[i] = days[i]+dates[i];
				}
			}
		}
	  
	  public int[] getChosenMonth(){
		  return monthsInWeek;
	  }
	  
	  public int[] getChosenWeekdays(){
		  return weekdays;
	  }
	  
	  public void setEvents(UserEvents ue){
		  
		  for(int i = 0; i < 7; i++){
			  eve = new ArrayList<objectTypes.Event>();
//			  ue.getEventsOnDay(new Date(111, mtw[i]-1, weekdays[i]));
			  TimeFrame day = new TimeFrame(weekdays[i], monthsInWeek[i], yr, 0, 0);
			  eve.addAll(ue.getEventsOnDay(day));
//			  System.out.println(new Date(111, mtw[i], weekdays[i]));
			  
			  if(eve.size()>0){
				  for(int j = 0; j < eve.size(); j++){
					  if(eve.get(j).getTime().getDayOfMonth()==weekdays[i]){
						  int datetemp = eve.get(j).getTime().getDayOfMonth();
						  int[] date = {i};
						  int start = eve.get(j).getTime().getStartHour();
						  int end = eve.get(j).getTime().getEndHour();
						  if(end==0){
							  end=24;
						  }
						  fill(start,end);
						  cellAtt.combine(filltime, date);
						  if(eve.get(j) instanceof Meeting){
							  if(((Meeting)eve.get(j)).getStatus()==Meeting.Status.ALL_ACCEPTED){
								  week.setValueAt("<HTML><P>"+eve.get(j).getTitle() + "</P><P BGCOLOR=\"55ff55\">Bitches are ready</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></HTML>", start, (i));
							  }else if(((Meeting)eve.get(j)).getStatus()==Meeting.Status.ONE_OR_MORE_DECLINED){
								  week.setValueAt("<HTML><P>"+eve.get(j).getTitle() + "</P><P BGCOLOR=\"ff5555\">One or more has Declined</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></HTML>", start, (i));
							  }else if(((Meeting)eve.get(j)).getStatus()==Meeting.Status.WAITING_FOR_ANSWER){
								  week.setValueAt("<HTML><P>"+eve.get(j).getTitle() + "</P><P BGCOLOR=\"ffff55\">Waiting for answer</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></HTML>", start, (i));
							  }else{
								  week.setValueAt("<HTML><P>"+eve.get(j).getTitle() + "</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></HTML>", start, (i));
							  }
						  }else{
							  week.setValueAt("<HTML><P>"+eve.get(j).getTitle() + "</P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></HTML>", start, (i));
						  }
						  week.clearSelection();
						  week.revalidate();
						  week.repaint();
					  }
				  }
			  }
		  }
	  }
	  
	  public void setOneSelect(){
		  week.setRowSelectionInterval(getStartTime(), getStartTime());
	  }
	  
	  public void setMultipleSelect(){
// TODO
	  }
	  
	  public void setEventDouble(UserEvents ue, UserEvents ue2){
		  for(int i = 0; i < 7; i++){
			  eve = new ArrayList<objectTypes.Event>();
			  eve2 = new ArrayList<objectTypes.Event>();
			  
			  TimeFrame day = new TimeFrame(weekdays[i], monthsInWeek[i], yr, 0, 0);
			  eve.addAll(ue.getEventsOnDay(day));
			  eve2.addAll(ue2.getEventsOnDay(day));
//			  System.out.println(new Date(111, mtw[i], weekdays[i]));
			  if(eve.size()>0){
				  for(int j = 0; j < eve.size(); j++){
					  if(eve.get(j).getTime().getDayOfMonth()==weekdays[i]){
						  int datetemp = eve.get(j).getTime().getDayOfMonth();
						  int[] date = {i*2};
						  int start = eve.get(j).getTime().getStartHour();
						  int end = eve.get(j).getTime().getEndHour();
						  if(end==0){
							  end=24;
						  }
						  fill(start,end);
						  cellAtt.combine(filltime, date);
						  if(eve.get(j) instanceof Meeting){
							  if(((Meeting)eve.get(j)).getStatus()==Meeting.Status.ALL_ACCEPTED){
								  week.setValueAt("<HTML><BODY BGCOLOR=\"B8CFE5\"><P>"+eve.get(j).getTitle() + "</P><P BGCOLOR=\"55ff55\">Bitches are ready</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></BODY></HTML>", start, (i*2));
							  }else if(((Meeting)eve.get(j)).getStatus()==Meeting.Status.ONE_OR_MORE_DECLINED){
								  week.setValueAt("<HTML><BODY BGCOLOR=\"B8CFE5\"><P>"+eve.get(j).getTitle() + "</P><P BGCOLOR=\"ff5555\">One or more has Declined</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></BODY></HTML>", start, (i*2));
							  }else if(((Meeting)eve.get(j)).getStatus()==Meeting.Status.WAITING_FOR_ANSWER){
								  week.setValueAt("<HTML><BODY BGCOLOR=\"B8CFE5\"><P>"+eve.get(j).getTitle() + "</P><P BGCOLOR=\"ffff55\">Waiting for answer</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></BODY></HTML>", start, (i*2));
							  }else{
								  week.setValueAt("<HTML><BODY BGCOLOR=\"B8CFE5\"><P>"+eve.get(j).getTitle() + "</P><P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></BODY></HTML>", start, (i*2));
							  }
						  }else{
							  week.setValueAt("<HTML><BODY BGCOLOR=\"B8CFE5\"><P>"+eve.get(j).getTitle() + "</P>" + eve.get(j).getTime().getStartHour() +"-"+ eve.get(j).getTime().getEndHour()+"</P></BODY></HTML>", start, (i*2));

						  }
						  week.clearSelection();
						  week.revalidate();
						  week.repaint();
					  }
				  }
			  }
			  if(eve2.size()>0){
				  for(int j = 0; j < eve2.size(); j++){
					  if(eve2.get(j).getTime().getDayOfMonth()==weekdays[i]){
						  int datetemp2 = eve2.get(j).getTime().getDayOfMonth();
						  int[] date = {i*2+1};
						  int start2 = eve2.get(j).getTime().getStartHour();
						  int end2 = eve2.get(j).getTime().getEndHour();
						  if(end2==0){
							  end2=24;
						  }
						  fill2(start2,end2);
						  cellAtt.combine(filltime2, date);
						  week.setValueAt("<HTML><BODY BGCOLOR=\"CCFFCC\"><P>"+eve2.get(j).getTitle() + "</P>" + eve2.get(j).getTime().getStartHour() +"-"+ eve2.get(j).getTime().getEndHour()+"</P></BODY></HTML>", start2, (i*2+1));
						  week.clearSelection();
						  week.revalidate();
						  week.repaint();
					  }
				  }
			  }
		  }
	  }
	  
	  private void fill(int a,int b){
		  filltime = new int[b-a];
		  for(int i = 0; i < filltime.length; i++){
			  filltime[i] = a;
			  a++;
		  }
	  }
	  
	  private void fill2(int a,int b){
		  filltime2 = new int[b-a];
		  for(int i = 0; i < filltime2.length; i++){
			  filltime2[i] = a;
			  a++;
		  }
	  }
	  
	
	  private class ms implements MouseListener{
	
		@Override
		public void mouseClicked(MouseEvent arg0) {
//			cx2 = week.getSelectedColumn();
//			System.out.println("test");
//			System.out.println(cx2);
//			week.setColumnSelectionInterval(cx2, cx2);
		}
	
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}
	
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		  
	  }
	  
	  public class AttributiveCellRenderer extends JLabel  implements
	  TableCellRenderer {

	    public AttributiveCellRenderer() {
	      setOpaque(true);
	    }
	  


	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
//		if(row==cys){
//			this.setBackground(Color.green);
//		}else{
//			this.setBackground(Color.white);
//		}
		this.setText(""+value);
//		System.out.println("test");
		
		
		return this;
	}
	  }
	
	public class AttributiveCellRenderer2 extends JLabel  implements TableCellRenderer {

	    public AttributiveCellRenderer2() {
	      setOpaque(true);
	    }

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			if(value==null){
				setText("");
			}else{
				setText(""+value);
			}
			
			if(isSelected||hasFocus){
				this.setBackground(Color.cyan);
				this.setForeground(Color.cyan);
			}
			this.setBackground(Color.white);
			
			return this;
		}
	  
	//  public static void main(String[] args) {
	//    weekCal2 frame = new weekCal2();
	//    frame.addWindowListener( new WindowAdapter() {
	//      public void windowClosing( WindowEvent e ) {
	//        System.exit(0);
	//      }
	//    });
	//  }
	}
	
}
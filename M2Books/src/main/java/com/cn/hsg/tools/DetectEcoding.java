/**
 * 
 */
package com.cn.hsg.tools;

import java.net.MalformedURLException;
import java.net.URL;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.forfuture.tools.Tools;
import com.forfuture.urlstream.BytesEncodingDetect;
import com.forfuture.urlstream.urlconnector;


/**
 * @author ZHAO
 * 探测编码方式
 */
public class DetectEcoding
{
	private String ecoding="gb2312";//这个编码方式是在探测不到编码方式的情况下使用的，所以最好更改成报纸的编码方式，以免万一
	private String htmlStream;
	private Tools tools;
	public DetectEcoding()
	{
		tools=new Tools();
	}
	public void setUrl(String url)
	{
		if(url!=null)
		{
			url=url.replace(" ","%20");
		}
		urlconnector uc=new urlconnector();
		this.htmlStream=uc.getContent(url);
	}
	public DetectEcoding(String html)
	{
		this.htmlStream=html;
	}
	public String getEcoding(String url)
	{
		if(url!=null)
		{
			url=url.replace(" ","%20");
		}
		URL url1=null;
		BytesEncodingDetect s = new BytesEncodingDetect(); 
		try
		{
			url1 = new URL(url);
		} 
		catch (MalformedURLException e)
		{
			return ecoding;
		}
		int ec=s.detectEncoding(url1);
		if(ec<0)
		{
			return "utf8";
		}
		ecoding=BytesEncodingDetect.nicename[ec];
		if(ecoding==null||ecoding.trim().equals("")||ecoding.equals("OTHER"))
		{
			return "utf8";
		}
		else
		{
			return ecoding.replace("-", "").trim();
		}
//		String a="";
//		try
//		{
//			a=tools.getContent(url, ecoding);
//		} catch (IOException e)
//		{
//			return ecoding;
//		}
//		ArrayList<String> mate=tools.getByREX(a, "<meta\\s*[^<>]+charset[^<>]+>");
//		for(int i=0;i<mate.size();i++)
//		{
//			if(mate.get(i).contains("gb2312"))
//				ecoding ="gb2312";
//			
//			if(mate.get(i).contains("gbk"))
//				ecoding = "gbk";
//			
//			if(mate.get(i).toLowerCase().contains("utf-8"))
//				ecoding = "utf-8";
//			
//			if(mate.get(i).contains("utf8"))
//				ecoding = "utf-8";
//			
//			if(mate.get(i).contains("iso-8859-1"))
//				ecoding = "iso-8859-1";
//		}
//		return ecoding;
	}
	public  String getEcoding()
	{
		Parser parser=new Parser();
		NodeList imglist=null;
		try
		{
			parser.setInputHTML(this.htmlStream);
			NodeClassFilter imgncf=new NodeClassFilter(MetaTag.class);  //定义过滤器。过滤器有很多种，比如“or”过滤器等。你自己看吧
			imglist =parser.extractAllNodesThatMatch(imgncf); //用上边定义的ncf过滤器解析页面，并把结果放到list里边，list是一个链表
		}
		catch (ParserException e)
		{
			e.printStackTrace();
		}
		for(int i=0;i<imglist.size();i++)
		{
			String ec=imglist.elementAt(i).getText().toLowerCase();
			if(ec.contains("gb2312"))
				return "gb2312";
			
			if(ec.contains("gbk"))
				return "gbk";
			
			if(ec.toLowerCase().contains("utf-8"))
				return "utf-8";
			
			if(ec.contains("utf8"))
				return "utf-8";
			
			if(ec.contains("iso-8859-1"))
				return "iso-8859-1";
		}
		return ecoding;
	}
	public void setEcoding(String ecoding)
	{
		this.ecoding = ecoding;
	}
	public String getHtmlStream()
	{
		return htmlStream;
	}
	public void setHtmlStream(String htmlStream)
	{
		this.htmlStream = htmlStream;
	}
}

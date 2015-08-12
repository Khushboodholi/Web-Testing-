/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;


    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. 
     * 
     * @param url
     *            - The URL to visit
     * 
     */
    public void crawl(String url)
    {
        Spider s = new Spider();
        //Connection connection = null; 
         //Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument1 = connection.get();
            this.htmlDocument = htmlDocument1;
            
            //Findind the broken links for Image Link
            Elements media = htmlDocument.select("[src]");
            
        for (Element src : media) {
            if (src.tagName().equals("img"))
            {
               String str= src.attr("abs:src");
            
            URL url1=new URL(str);
            //System.out.println(src.attr("abs:src"));  
            
            isLinkBroken(url1);                                    
                               
                                           
            }
        }
        
        
            
            
            
            
            
            
            
            if(connection.response().statusCode()!= 200) // 200 is the HTTP OK status code
                                                          
            {
                System.out.println( url+" Broken Links with code --"+connection.response().statusCode());
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println(url+"**Failure** Retrieved something other than HTML");
                //return false;
            }
            else{
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println(" Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                if(link.attr("abs:href").contains("https://parasol.tamu.edu"))
                    { 
                        if (link.attr("abs:href").contains("#"))
                        {
                           String str= link.absUrl("href");
                            str=str.substring(0, (str.indexOf("#")));
                           
                            this.links.add(str);
                            
                        }
                        else
                        {
                    
                            this.links.add(link.absUrl("href"));
                        }
                   
                }
            }
        
        }
        }
        catch(IOException ioe)
                { 
                System.out.println(" Invalid URL : No Links Found");
                 
           
            
            // We were not successful in our HTTP request
            
        }
    }


    
    
    /**
     * This method should
     * only be called after a successful crawl.
     * 
     * 
     *            
     * @return the link
     */
    public List<String> getLinks()
    {
        return this.links;
    }
    
    
    
    
    public static void isLinkBroken(URL url) //Check if the image Link is broken or not
 
	{
 
	   
	     
 
		try
 
                {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
 
		     String response = connection.getResponseMessage();	        
 
		    connection.disconnect();
                   
                     if (connection.getResponseCode()!=(200)){
		    System.out.println("Broken Image Link : "+url+"...."+response+"..."+connection.getResponseCode());
                     }
                     
		}
 
		catch(Exception exp)
 
		{
 
			System.out.println("Invaid Image URL"+url);
 		     
		}  				
 
	}
    
    

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brokenlink;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
public class BrokenLink {
    
    public static void main(String[] args) {
       
        //type the URL which needs to be tested
        
       String url="https://parasol.tamu.edu";
       int count=1; 
     
        try {
            //Connect with the URl using Jsoup library and get all the Hyper links
            Document doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements links = doc.select("a[href]");
             
            for(Element e : links)
            {
               //1.Check all links Starting with # and redirect it to main URL n check if broken or not
               if(e.attr("href").substring(0,1).equals("#"))
                {
                       
                    count=connection(url,count);
                }
            
              //2.Check all links Statring with '/',append http and check if broken 
               else if (e.attr("href").substring(0,1).equals("/") && !e.attr("href").substring(0,2).equals("//"))
                {
                   
                    count=join(e.attr("href"),url,count);
                }  
               
             //3.Check all links Statring with '//',append http and check if broken 
               else if (e.attr("href").substring(0,2).equals("//"))
                {
                   
                   //count=join(e.attr("href"),url,count);
                }
                
                 //4.Check all links Statring with 'http',append http and check if broken 
               else if(e.attr("href").substring(0,7).equals("http://"))
                        {
                           count=connection(e.attr("href"),count); 
                                                                       
                        }
                 //5.Check all links Statring with 'https',append http and check if broken 
               else if ( e.attr("href").substring(0,7).equals("https:/"))
                    {
                           count=connection(e.attr("href"),count); 
                                                                       
                     }
                // 6.Check all links Statring with '.',append http and check if broken
               else if(e.attr("href").substring(0,1).equals("."))
                {
                   String s= e.attr("href").substring(1);
                  count=join(s,url,count);
                   
                 }
               
               //7. Check all other links 
               else{
               if(!e.attr("href").substring(0,7).equals("mailto:"))
               {
                count=join(e.attr("href"),url,count);
               }
               }
            
                
                }
            }
        catch (IOException ex){
           
            System.out.println("Invalid URL");
                                }
        if(count!=0)
        {
        System.out.println("No Broken link");
        }
            
            
    }
    
    
    
    
    
    //Conncetion Function
    public static int connection(String str,int temp)
    {
     try{
          URL oracle = new URL(str);
          URLConnection yc = oracle.openConnection();
           yc.connect();
           return (temp*1);
         
         }catch(Exception g)
           { 
            System.out.println(str+"          -Broken Link");
            return (temp*0);
           }
    }
    
    //Appending the URL 
    public static int join(String attr,String url,int temp)
    {
                         int count;
                        if(attr.substring(0,1).equals("/"))
                        {
                        String Str2=url.concat(attr);
                        count=connection(Str2,temp);
                        return(count);
                        }
                        else if(attr.substring(0,2).equals("//")||attr.substring(0,1).equals("."))
                        {
                         String Str2=url.concat(attr.substring(1));
                          count=connection(Str2,temp);
                        return(count);
                        }
                        else
                        {
                            
                            url=url.concat("/");
                            String Str2=url.concat(attr);
                            count=connection(Str2,temp);
                            return(count);
                        }
    
    }

}

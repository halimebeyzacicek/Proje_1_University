package com.example.xmlparsingkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {

    internal lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.textView) as TextView
        val pullParserFactory:XmlPullParserFactory
        try {
            pullParserFactory= XmlPullParserFactory.newInstance()
            val parser=pullParserFactory.newPullParser()
            val inputStream=applicationContext.assets.open("sample.xml")
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false)
            parser.setInput(inputStream,null)

            val countries= parseXML(parser)
            var text=""

            for (country in countries!!){
                text+="id : "+country.id+"name : "+ country.name+"capital : +"+ country.capital+"\n"

            }
            textView.text=text
        }catch (e:XmlPullParserException){
            e.printStackTrace()
        }catch (e:IOException){
            e.printStackTrace()
        }

    }
    @Throws(XmlPullParserException::class,IOException::class)
    fun parseXML(parser:XmlPullParser):ArrayList<Country>?{
        var countries:ArrayList<Country>?= null
        var eventType=parser.eventType
        var country:Country?=null

        while (eventType!=XmlPullParser.END_DOCUMENT){
            val name:String
            when(eventType){
                XmlPullParser.START_DOCUMENT->countries= ArrayList()
                XmlPullParser.START_TAG->{
                    name=parser.name
                    if(name=="country"){
                        country= Country()
                        country.id=parser.getAttributeValue(null,"id")
                    }else if(country!=null){
                        if(name=="name"){
                            country.name=parser.nextText()
                        }else if(name=="capital"){
                            country.capital=parser.nextText()
                        }
                    }
                }
                XmlPullParser.END_TAG->{
                    name=parser.name
                    if(name.equals("country",ignoreCase = true)&&country!=null){
                        countries!!.add(country)
                    }
                }
            }
            eventType=parser.next()
        }
        return countries
    }
}

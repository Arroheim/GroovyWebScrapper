package ru.consoleTools

import groovy.time.TimeCategory
import groovy.time.TimeDuration
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import ru.consoleTools.helpers.TUIHelper


class Scrapper {
    static void main(String... args) {
        StringBuffer out = new StringBuffer()
        int spaces = 75
        int progress = 50000
        File file = new File("build/test.txt")
        println("Новости".center(30, '-') << "\n")
        println("Пожалуйста подождите...")
        Date start = new Date()
        (progress + 1).times  {
            String hash = "▒" * Math.ceil((it*spaces)/progress)
            print(String.format("[%-" + spaces + "s] %d%s\r", hash, it.intdiv(progress.intdiv(100)), '%'))
            Document document = Jsoup.connect("http://www.opennet.ru/opennews/art.shtml?num="+it).get()
            String title = document.select("#r_title").text()
            String date = document.select("#as2 > form > table:nth-child(1) > tbody > tr > td > h2 > font").text()
            out << "|".padRight(5)+title.take(45).padLeft(5).padRight(50) + " | " + date.padRight(17) + "|" << "\n"
        }
        Date stop = new Date()
        TimeDuration td = TimeCategory.minus( stop, start )
        print("\nГотово. Операция заняла: "+td+"\n")
        file.write("-"*75+"\n"+out.toString()+"-"*75)
        println("-"*75)
        print(out)
        println("-"*75)


    }
}
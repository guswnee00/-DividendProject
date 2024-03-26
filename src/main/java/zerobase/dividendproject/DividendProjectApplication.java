package zerobase.dividendproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zerobase.dividendproject.model.Company;
import zerobase.dividendproject.scraper.Scraper;
import zerobase.dividendproject.scraper.YahooFinanceScraper;

@SpringBootApplication
public class DividendProjectApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DividendProjectApplication.class, args);
        Scraper scraper = new YahooFinanceScraper();
        //var result = scraper.scrap(Company.builder().ticker("O").build());
        var result = scraper.scrapCompanyByTicker("MMM");
        System.out.println(result);
    }

}

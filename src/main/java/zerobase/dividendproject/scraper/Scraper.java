package zerobase.dividendproject.scraper;

import zerobase.dividendproject.model.Company;
import zerobase.dividendproject.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}

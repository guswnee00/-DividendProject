package zerobase.dividendproject.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.dividendproject.model.Company;
import zerobase.dividendproject.model.ScrapedResult;
import zerobase.dividendproject.model.constants.CacheKey;
import zerobase.dividendproject.persist.CompanyRepository;
import zerobase.dividendproject.persist.DividendRepository;
import zerobase.dividendproject.persist.entity.CompanyEntity;
import zerobase.dividendproject.persist.entity.DividendEntity;
import zerobase.dividendproject.scraper.Scraper;

import java.util.List;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    private final Scraper yahooFinanceScraper;

    // 일정 주기마다 수행
    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")   // 매일 정각 실행
    public void yahooFinanceScheduling() {
//        log.info("scraping scheduler is started");
        // 저장된 회사 목록 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();

        // 회사마다 배당금 정보를 새로 스크래핑
        for (var company: companies) {
            log.info("scraping scheduler is started -> " + company.getName());
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(new Company(company.getTicker(), company.getName()));

            // 스크래핑한 배당금 정보 중 데이터베이스에 없는 값은 저장
            scrapedResult.getDividends().stream()
                    // dividend 모델을 dividend entity로 매핑
                    .map(e -> new DividendEntity(company.getId(), e))
                    // element를 하나씩 dividend repository에 삽입
                    .forEach(e -> {
                        boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                        if (!exists) {
                            this.dividendRepository.save(e);
                        }
                    });

            // 연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);   // 3 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


    }
}
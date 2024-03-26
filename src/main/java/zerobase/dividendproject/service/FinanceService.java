package zerobase.dividendproject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.dividendproject.model.Company;
import zerobase.dividendproject.model.Dividend;
import zerobase.dividendproject.model.ScrapedResult;
import zerobase.dividendproject.persist.CompanyRepository;
import zerobase.dividendproject.persist.DividendRepository;
import zerobase.dividendproject.persist.entity.CompanyEntity;
import zerobase.dividendproject.persist.entity.DividendEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public ScrapedResult getDividendByCompanyName(String companyName) {
        // 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                                            .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));

        // 조회한 회사 ID로 배당금 정보 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        // 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                                                    .map(e -> Dividend.builder()
                                                            .date(e.getDate())
                                                            .dividend(e.getDividend())
                                                            .build())
                                                    .collect(Collectors.toList());

        return new ScrapedResult(Company.builder()
                                        .ticker(company.getTicker())
                                        .name(company.getName())
                                        .build()
                                , dividends);
    }
}

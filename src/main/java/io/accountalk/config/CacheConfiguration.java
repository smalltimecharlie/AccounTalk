package io.accountalk.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.accountalk.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.accountalk.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.accountalk.domain.User.class.getName());
            createCache(cm, io.accountalk.domain.Authority.class.getName());
            createCache(cm, io.accountalk.domain.User.class.getName() + ".authorities");
            createCache(cm, io.accountalk.domain.Client.class.getName());
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".addresses");
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".businesses");
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".previousAccountants");
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".taxReturns");
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".bankDetails");
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".shares");
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".pensionProviders");
            createCache(cm, io.accountalk.domain.Client.class.getName() + ".employments");
            createCache(cm, io.accountalk.domain.Address.class.getName());
            createCache(cm, io.accountalk.domain.Employment.class.getName());
            createCache(cm, io.accountalk.domain.PreviousAccountant.class.getName());
            createCache(cm, io.accountalk.domain.PreviousAccountant.class.getName() + ".addresses");
            createCache(cm, io.accountalk.domain.Business.class.getName());
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName());
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".pensionReceiveds");
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".personalPensionContributions");
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".giftAidDonations");
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".bankInterests");
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".dividendsReceiveds");
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".employmentDetails");
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".eisInvestments");
            createCache(cm, io.accountalk.domain.TaxReturn.class.getName() + ".businessProfits");
            createCache(cm, io.accountalk.domain.PensionProvider.class.getName());
            createCache(cm, io.accountalk.domain.PensionReceived.class.getName());
            createCache(cm, io.accountalk.domain.BankInterest.class.getName());
            createCache(cm, io.accountalk.domain.PerPensionContributions.class.getName());
            createCache(cm, io.accountalk.domain.StatePensionReceived.class.getName());
            createCache(cm, io.accountalk.domain.GiftAidDonations.class.getName());
            createCache(cm, io.accountalk.domain.BankDetails.class.getName());
            createCache(cm, io.accountalk.domain.DividendsReceived.class.getName());
            createCache(cm, io.accountalk.domain.Shares.class.getName());
            createCache(cm, io.accountalk.domain.EmpPensionContributions.class.getName());
            createCache(cm, io.accountalk.domain.EmploymentDetails.class.getName());
            createCache(cm, io.accountalk.domain.EmploymentDetails.class.getName() + ".earnings");
            createCache(cm, io.accountalk.domain.EmploymentDetails.class.getName() + ".benefits");
            createCache(cm, io.accountalk.domain.EmploymentDetails.class.getName() + ".expenses");
            createCache(cm, io.accountalk.domain.EmploymentDetails.class.getName() + ".employmentPensionContributions");
            createCache(cm, io.accountalk.domain.BusinessProfit.class.getName());
            createCache(cm, io.accountalk.domain.Earnings.class.getName());
            createCache(cm, io.accountalk.domain.Benefits.class.getName());
            createCache(cm, io.accountalk.domain.Expenses.class.getName());
            createCache(cm, io.accountalk.domain.EisInvestments.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}

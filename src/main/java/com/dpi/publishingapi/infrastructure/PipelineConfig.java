package com.dpi.publishingapi.infrastructure;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.dpi.publishingapi.features.authentication.local.login.UserLoginHandler;
import com.dpi.publishingapi.features.authentication.local.signup.UserSignupHandler;
import com.dpi.publishingapi.features.authentication.local.verification.UserVerificationHandler;
import com.dpi.publishingapi.features.authentication.oauth.login.OauthLoginHandler;
import com.dpi.publishingapi.features.authentication.oauth.signup.OauthSignupHandler;
import com.dpi.publishingapi.features.authentication.refresh.refresh_token.RefreshUserTokenHandler;
import com.dpi.publishingapi.features.books.book.create.CreateBookHandler;
import com.dpi.publishingapi.features.books.book.data.get.GetBookDataHandler;
import com.dpi.publishingapi.features.books.book.get.GetBookHandler;
import com.dpi.publishingapi.features.books.book.get_all.GetAllBooksHandler;
import com.dpi.publishingapi.features.books.book.library.get.GetUserBooksHandler;
import com.dpi.publishingapi.features.books.book.search.SearchBooksHandler;
import com.dpi.publishingapi.features.books.creator.get_all_creators.GetAllCreatorsHandler;
import com.dpi.publishingapi.features.books.language.get_all_languages.GetAllLanguagesHandler;
import com.dpi.publishingapi.features.books.publisher.get_all_publishers.GetAllPublishersHandler;
import com.dpi.publishingapi.features.books.type.get_all_types.GetAllTypesHandler;
import com.dpi.publishingapi.features.payment.purchase.capture_purchase.PurchaseCaptureHandler;
import com.dpi.publishingapi.features.payment.purchase.create_purchase.PurchaseCreationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
public class PipelineConfig {

    @Autowired
    private PurchaseCreationHandler purchaseCreationHandler;
    @Autowired
    private PurchaseCaptureHandler purchaseCaptureHandler;
    @Autowired
    private GetUserBooksHandler getUserBooksHandler;
    @Autowired
    private GetBookDataHandler getUserBookDataHandler;
    @Autowired
    private GetAllLanguagesHandler getAllLanguagesHandler;
    @Autowired
    private GetAllTypesHandler getAllTypesHandler;
    @Autowired
    private GetAllPublishersHandler getAllPublishersHandler;
    @Autowired
    private GetAllCreatorsHandler getAllCreatorsHandler;
    @Autowired
    private SearchBooksHandler searchBooksHandler;
    @Autowired
    private GetAllBooksHandler getAllBooksHandler;
    @Autowired
    private UserSignupHandler userSignupHandler;
    @Autowired
    private UserLoginHandler userLoginHandler;
    @Autowired
    private UserVerificationHandler userVerificationHandler;
    @Autowired
    private OauthLoginHandler oauthLoginHandler;
    @Autowired
    private OauthSignupHandler oauthSignupHandler;
    @Autowired
    private RefreshUserTokenHandler refreshUserTokenHandler;
    @Autowired
    private CreateBookHandler createBookHandler;
    @Autowired
    private GetBookHandler getBookHandler;


    @Bean
    public Pipeline pipeline() {
        return new Pipelinr()
                .with(() -> Stream.of(purchaseCreationHandler,
                        purchaseCaptureHandler,
                        getUserBooksHandler,
                        getUserBookDataHandler,
                        getAllLanguagesHandler,
                        getAllTypesHandler,
                        getAllPublishersHandler,
                        getAllCreatorsHandler,
                        getAllBooksHandler,
                        searchBooksHandler,
                        getBookHandler,
                        userSignupHandler,
                        userLoginHandler,
                        userVerificationHandler,
                        oauthLoginHandler,
                        oauthSignupHandler,
                        refreshUserTokenHandler,
                        createBookHandler));
    }
}

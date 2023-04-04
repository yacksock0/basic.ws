package kr.onthelive.basic.ws.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.onthelive.basic.ws.configuration.support.TomcatProperties;
import kr.onthelive.basic.ws.util.SystemEnvUtil;
import org.apache.catalina.connector.Connector;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ApplicationConfiguration implements AsyncConfigurer {
//
//    @Value("${spring.servlet.encoding.charset}")
//    private String encoding;
//
//    @Value("${basic.ws.file.access.aws-url}")
//    private String awsUrl;
//
//    @Value("${basic.ws.file.access.oci-access-url}")
//    private String ociAccessUrl;
//
//    @Value("${basic.ws.file.access.oci-name-space}")
//    private String ociNameSpace;
//
//    @Value("${basic.ws.file.access.aws.region-name}")
//    private String regionName;
//
//    @Value("${basic.ws.file.access.aws.bucket-name}")
//    private String bucketName;
//
//    @Value("${basic.ws.file.access.aws.access-key}")
//    private String accessKey;
//
//    @Value("${basic.ws.file.access.aws.secret-key}")
//    private String secretKey;
//
//    @Value("${basic.ws.file.access.oci-folder-name}")
//    private String ociFolderName;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(20);
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    // Tomcat configuration
    @Bean
    public TomcatServletWebServerFactory servletContainer(TomcatProperties tomcatProperties) {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);

                connector.setProperty("maxThreads", 			tomcatProperties.getMaxThreads());
                connector.setProperty("minSpareThreads", 		tomcatProperties.getMinSpareThreads());
                connector.setProperty("maxConnections",		    tomcatProperties.getMaxConnections());
                connector.setProperty("connectionLinger", 	    tomcatProperties.getConnectionLingers());
                connector.setProperty("connectionTimeout", 	    tomcatProperties.getConnectionTimeout());
                connector.setProperty("keepAliveTimeout", 	    tomcatProperties.getKeepAliveTimeout());
                connector.setProperty("maxKeepAliveRequests",   tomcatProperties.getMaxKeepAliveRequests());
                connector.setProperty("server", 				tomcatProperties.getServerInfo());
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SystemEnvUtil systemEnvUtil() {
        return new SystemEnvUtil();
    }

    @Bean
    public ModelMapper modelMapper() {
        Provider<LocalDateTime> localDateTimeProvider = new AbstractProvider<LocalDateTime>() {
            @Override
            public LocalDateTime get() {
                return LocalDateTime.now();
            }
        };

        Converter<String, LocalDateTime> toStringDate = new AbstractConverter<String, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(source, format);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(String.class, LocalDateTime.class);
        modelMapper.addConverter(toStringDate);
        modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(localDateTimeProvider);
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

//    @Bean
//    public FileUploader fileUploader() {
//        return new OCIObjectStorageUploader(encoding, ociAccessUrl, ociNameSpace, bucketName, awsUrl, regionName, accessKey, secretKey, ociFolderName);
//    }

//    @Bean
//    public FileUtil fileUtil(){
//        return new FileUtil();
//    }

//    @Bean
//    public ExcelUtil excelUtil(ObjectMapper objectMapper,
//                               CommonService commonService,
//                               ProjectRepository projectRepository,
//                               SegmentRepository segmentRepository,
//                               JobSubRepository jobSubRepository,
//                               JobSubResultRepository jobSubResultRepository,
//                               JobSubRejectRepository jobSubRejectRepository){
//        return new ExcelUtil(objectMapper, commonService, projectRepository, segmentRepository, jobSubRepository, jobSubResultRepository, jobSubRejectRepository);
//    }


}

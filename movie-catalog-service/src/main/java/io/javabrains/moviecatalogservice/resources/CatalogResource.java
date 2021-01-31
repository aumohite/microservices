package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    
	//@Autowired WebClient.Builder webClientBuilder;

//    	@RequestMapping("/foo")
//        public String getCatalog() {
//    		return "HI";
//    	}
    	
    	
//    	@RequestMapping("/{foo}")
//        public String getCatalog(@PathVariable("foo") String user) {
//    		return "HI" + "---" +user;
//    	}

    

//    @RequestMapping("/{userId}")
//    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//    	return Collections.singletonList(new CatalogItem("titanic", "Nice Movie", 4));
//	}
    
//    @RequestMapping("/{userId}")
//    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//    	//get ratings from ratings-data-service actually,but for now hardcode it.
//    	List<Rating> ratings  = Arrays.asList(
//    			new Rating("1234",4),
//    			new Rating("5678",3)
//    			);
//    	//for each rating call the movies-info-service actually-but for now hardcode it.
//    	return ratings.stream().map(rating -> 
//    								new CatalogItem("titanic","Nice Movie",4)
//    								).collect(Collectors.toList());
//	}
    
    
    //call to 1 rest services
//    @RequestMapping("/{userId}")
//    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//
//    	//get ratings from ratings-data-service actually,but for now hardcode it.
//    	List<Rating> ratings  = Arrays.asList(
//    			new Rating("1234",4),
//    			new Rating("5678",3)
//    			);
//    	//for each rating call the movies-info-service actually-but for now hardcode it.
//    	return ratings.stream().map(rating -> 
//    								{
//    								  Movie movie  = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
//    								  return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
//    								}
//    								).collect(Collectors.toList());
//	}
    
    //call to 2 rest services
//    @RequestMapping("/{userId}")
//    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//
//    	//get ratings from ratings-data-service actually,but for now hardcode it.
//    	//ParameterizedTypeReference<List<Rating>> parameterizedTypeReference = new ParameterizedTypeReference<List<Rating>>() {};
//    	//List<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, List.class);
//    	//System.out.println("ratings :::::::::::::::::::::::: "+ratings);
//    	
//    	UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);
//
//    	//for each rating call the movies-info-service actually-but for now hardcode it.
//    	return userRating.getRatings().stream().map(rating -> 
//    								{
//    								  Movie movie  = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
//    								  return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
//    								}
//    								).collect(Collectors.toList());
//	}
    
    //using the application names from Application.properties file instead of the "hostname(localhost):port"
    //disocvery server has these application names registered with it .
    //So it wil map to the actual movies serves & rating services
    //thus "localhost:8083" --> will become "ratings-data-service" & thus "localhost:8082" --> will become "movie-info-service"
//    @RequestMapping("/{userId}")
//    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//
//    	//get ratings from ratings-data-service actually,but for now hardcode it.
//    	//ParameterizedTypeReference<List<Rating>> parameterizedTypeReference = new ParameterizedTypeReference<List<Rating>>() {};
//    	//List<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, List.class);
//    	//System.out.println("ratings :::::::::::::::::::::::: "+ratings);
//    	
//    	UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
//
//    	//for each rating call the movies-info-service actually-but for now hardcode it.
//    	return userRating.getRatings().stream().map(rating -> 
//    								{
//    								  Movie movie  = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
//    								  return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
//    								}
//    								).collect(Collectors.toList());
//	}
    
    //-hete circuit brsaker can be used as thi sis the place where in other url are alled
    @RequestMapping("/{userId}")
    //@HystrixCommand(fallbackMethod = "getCatalogFallback")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    	//get ratings from ratings-data-service actually,but for now hardcode it.
    	//ParameterizedTypeReference<List<Rating>> parameterizedTypeReference = new ParameterizedTypeReference<List<Rating>>() {};
    	//List<Rating> ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, List.class);
    	//System.out.println("ratings :::::::::::::::::::::::: "+ratings);
    	
    	UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
//System.out.println("--------userRating.getRatings()--------------"+userRating.getRatings());
    	List<Rating> ratings = userRating.getRatings();
for (Rating rating2 : ratings) {
	System.out.println("MOVIE ID DD == "+rating2.getMovieId());
}
    	//for each rating call the movies-info-service actually-but for now hardcode it.
    	return userRating.getRatings().stream().map(rating -> 
    								{
    								  Movie movie  = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
    								  return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
    								}
    								).collect(Collectors.toList());
	}
    
    
//    public List<CatalogItem> getCatalogFallback(@PathVariable("userId") String userId) {
//    	return Arrays.asList(new CatalogItem("No Movie Availble", "No Movie Desc", 0) );
//    }
    
//    @RequestMapping("/{userId}")
//    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//
//        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
//
//        return userRating.getRatings().stream()
//                .map(rating -> {
//                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
//                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
//                })
//                .collect(Collectors.toList());
//
//    }
        	
    
}

/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/
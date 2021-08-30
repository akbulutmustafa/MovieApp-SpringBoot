package com.moviePage.movieP.restApi;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moviePage.movieP.Scraper;
import com.moviePage.movieP.Buisness.IMovieService;
import com.moviePage.movieP.Buisness.IShowService;
import com.moviePage.movieP.Buisness.IUserService;
import com.moviePage.movieP.Entities.Movie;
import com.moviePage.movieP.Entities.Show;
import com.moviePage.movieP.Entities.User;

@Controller
@RequestMapping("/")
public class MovieAppController {
	
	@Autowired
	private IMovieService movieService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IShowService showService;
	
	@RequestMapping("/")
	public String viewHome(Model model) {
		Scraper scrap = new Scraper();
		List<Movie> movies = new ArrayList<Movie>();
		List<Show> shows = new ArrayList<Show>();
		movies = scrap.popMovies();
		shows = scrap.popShows();
		model.addAttribute("movies", movies);
		model.addAttribute("shows", shows);
		return "home";
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam(name="word") String word, Model model) {
		Scraper scrap = new Scraper();
		List<Movie> movies = scrap.searchMovies(word);
		model.addAttribute("movies", movies);
		return "search-movies";
	}
	
	@RequestMapping("/movies")
	public String getMovies(Model model, @CurrentSecurityContext(expression="authentication?.name") String email) {
		//List<Movie> movies = movieService.getAll();
		//model.addAttribute("movies", movies);
		User user = userService.getByEmail(email);
		List<Movie> movies = movieService.getMovies(user.getId());
		List<Show> shows = showService.getShows(user.getId());
		
		model.addAttribute("moviesize", movies.size());
		model.addAttribute("showsize", shows.size());
		model.addAttribute("movies", movies);
		model.addAttribute("shows", shows);
		return "movies";
	}
	
	@RequestMapping("/movie/{id}")
	public String movieDetail(@PathVariable("id") int id, Model model) {
		Movie movie = movieService.getById(id);
		model.addAttribute("movie", movie);
		return "movie-details";
	}
	
	@RequestMapping("/ht/{id}")
	public String movieDetailHome(@PathVariable("id") String id, Model model) {
		Scraper scrape = new Scraper();
		Movie movie = scrape.movieDetails(id);
		String link = "https://www.youtube.com/embed/" + scrape.movieTrailer(id);//https://www.youtube.com/watch?v=
		model.addAttribute("movie", movie);
		model.addAttribute("link", link);
		//System.out.print(link);
		return "movie-details";
	}
	
	@RequestMapping("/shows/{id}")
	public String showDetailHome(@PathVariable("id") String id, Model model) {
		Scraper scrape = new Scraper();
		Show show = scrape.showDetails(id);
		String link = "https://www.youtube.com/embed/" + scrape.showTrailer(id);//https://www.youtube.com/watch?v=
		model.addAttribute("show", show);
		model.addAttribute("link", link);
		//System.out.print(link);
		return "show-detail";
	}
	
	@RequestMapping("/add-movie")
	public String addMovie(Model model) {
		Scraper scrapper = new Scraper();
		List<Scraper> movies = scrapper.search("blade runner");
		model.addAttribute("movies", movies);
		return "search-movies";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addMovie(@ModelAttribute("movie") Movie movie, Model model, @CurrentSecurityContext(expression="authentication?.name") String email) {
		User user = userService.getByEmail(email);
		movieService.add(movie, user);
		return "redirect:/movies";
	}
	
	@RequestMapping(value="/add-show", method=RequestMethod.POST)
	public String addShow(@ModelAttribute("show") Show show, Model model, @CurrentSecurityContext(expression="authentication?.name") String email) {
		User user = userService.getByEmail(email);
		showService.add(show, user);
		return "redirect:/movies";
	}
	
	@RequestMapping("done-show/{id}")
	public String makeItDones(@PathVariable("id") int id, Model model, @CurrentSecurityContext(expression="authentication?.name") String email) {
		Show show = showService.getById(id);
		if(show.isDid()) show.setDid(false);
		else show.setDid(true);
		
		User user = userService.getByEmail(email);
		List<Movie> movies = movieService.getMovies(user.getId());
		List<Show> shows = showService.getShows(user.getId());
		
		model.addAttribute("moviesize", movies.size());
		model.addAttribute("showsize", shows.size());
		model.addAttribute("movies", movies);
		model.addAttribute("shows", shows);
		return "redirect:/movies";
	}
	
	@RequestMapping("done-movie/{id}")
	public String makeItDonem(@PathVariable("id") int id, Model model, @CurrentSecurityContext(expression="authentication?.name") String email) {
		Movie movie = movieService.getById(id);
		if(movie.isDid()) movie.setDid(false);
		else movie.setDid(true);
		
		User user = userService.getByEmail(email);
		List<Movie> movies = movieService.getMovies(user.getId());
		List<Show> shows = showService.getShows(user.getId());
		
		model.addAttribute("moviesize", movies.size());
		model.addAttribute("showsize", shows.size());
		model.addAttribute("movies", movies);
		model.addAttribute("shows", shows);
		return "redirect:/movies";
	}
	
	@RequestMapping("edit/{id}")
	public String editMovie(@PathVariable("id") int id, Model model) {
		Movie movie = movieService.getById(id);
		model.addAttribute("movie", movie);
		return "update-movie";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public String updateMovie(Movie movie, Model model) {
		movieService.update(movie);
		model.addAttribute("movies", movieService.getAll());
		return "redirect:/movies";
	}
	
	@RequestMapping("admin/delete-movie/{id}")//(value="admin/delete-movie/{id}", method=RequestMethod.POST)
	public String deleteMovie(@PathVariable("id") int id, Model model) {
		Movie movie = movieService.getById(id);
		List<User> users = userService.getAll();
		for(User user: users) {
			List<Movie> movies  = user.getMovies();
			if(movies.contains(movie)) movies.remove(movie);
		}
		movieService.delete(movie);
		model.addAttribute("movies", movieService.getAll());
		return "redirect:/admin";
	}
	
	@RequestMapping("admin/delete-show/{id}")//(value="admin/delete-movie/{id}", method=RequestMethod.POST)
	public String deleteShow(@PathVariable("id") int id, Model model) {
		Show show = showService.getById(id);
		List<User> users = userService.getAll();
		for(User user: users) {
			List<Show> shows  = user.getShows();
			if(shows.contains(show)) shows.remove(show);
		}
		showService.delete(show);
		model.addAttribute("shows", showService.getAll());
		return "redirect:/admin";
	}
	
	@RequestMapping("admin/delete-user/{id}")//(value="admin/delete-user/{id}", method=RequestMethod.POST)
	public String deleteUser(@PathVariable("id") int id, Model model) {
		User user = userService.getById(id);
		user.setRoles(null);
		userService.delete(user);
		model.addAttribute("users", userService.getAll());
		return "redirect:/admin";
	}
	
	@RequestMapping(value="delete/{id}", method=RequestMethod.POST)
	public String deleteMovie(@PathVariable("id") int id, Model model, @CurrentSecurityContext(expression="authentication?.name") String email) {
		Movie movie = movieService.getById(id);
		User user = userService.getByEmail(email);
		user.deleteMovie(movie);
		model.addAttribute("movies", movieService.getAll());
		return "redirect:/movies";
	}
	
	@RequestMapping("/admin")
	public String adminPage(Model model) {
		model.addAttribute("movies", movieService.getAll());
		model.addAttribute("users", userService.getAll());
		model.addAttribute("shows", showService.getAll());
		return "admin-page";
	}
	
	/*@RequestMapping("/passrec")
	public String passwordRec(Model model, User user) {
		this.userService.passwordRec(user);
		return "redirect:/login";
	}*/
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/register")
	public String register(User user) {
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("message", "Please correct the errors in the form!");
			model.addAttribute("bindingResult", bindingResult);
		}
		else if(userService.isExist(user)) {
			model.addAttribute("message", "User alrady exist!");
		}
		else {
			userService.add(user);
			model.addAttribute("message", "User successfully registered.");
		}
		model.addAttribute("user", user);
		return "register";
	}
	
	@RequestMapping("/home")
	public String userHome() {
		return "register";
	}
	
}

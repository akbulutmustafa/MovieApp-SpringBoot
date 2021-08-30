package com.moviePage.movieP;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.moviePage.movieP.Entities.Movie;
import com.moviePage.movieP.Entities.Show;

public class Scraper {
	
	private String title;
	private String imgSrc;
	private String mid;

	public Scraper() {
	}
	
	public Scraper(String title, String imgSrc, String mid) {
		this.title = title;
		this.imgSrc = imgSrc;
		this.mid = mid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public List<Scraper> search(String toSearch) {//scrapper
		List<Scraper> mResults = new ArrayList<Scraper>();
		toSearch = String.join("+", toSearch.split(" "));
		try {
			Document doc = Jsoup.connect("https://www.imdb.com/find?q=" + toSearch).get();
			Element divTag = doc.select("table").first();
			if(divTag==null) {
				Scraper sc = new Scraper();
				sc.setTitle("No results found.");
				sc.setImgSrc("false");
				mResults.add(sc);
			}
			else {
				Elements d1 = divTag.select("tr");
				for(Element e: d1) {
					Scraper sc = new Scraper();
					sc.setTitle(e.getElementsByClass("result_text").text());
					sc.setImgSrc(e.getElementsByClass("primary_photo").select("img").attr("src"));
					mResults.add(sc);
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mResults;
	}
	
	public List<Scraper> popMoviesSc(){//scrapper
		List<Scraper> pops = new ArrayList<Scraper>();
		try {
			Document doc = Jsoup.connect("https://www.themoviedb.org/movie").get();
			Element divf = doc.getElementById("media_results");
			if(divf==null) {
				Scraper sc = new Scraper("Not Found", "Not Found", "0");
				pops.add(sc);
			}
			else {
				Elements cards = divf.getElementsByClass("style_1").not("[class~=filler]");
				for(Element card: cards) {
					Scraper sc = new Scraper();
					sc.setImgSrc("https://www.themoviedb.org/" + card.select("a[class=image]").select("img").attr("src"));
					String mid = Arrays.asList(card.select("h2").select("a").attr("href").split("/")).get(2);
					sc.setMid(mid);
					sc.setTitle(card.select("h2").text());
					pops.add(sc);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pops;
	}
	
	public List<Scraper> popShowsSc(){//scrapper
		List<Scraper> shows = new ArrayList<Scraper>();
		try {
			Document doc = Jsoup.connect("https://www.themoviedb.org/tv").get();
			Element divf = doc.getElementById("media_results");
			if(divf==null) {
				Scraper sc = new Scraper("Not Found", "Not Found", "0");
				shows.add(sc);
			}
			else {
				Elements cards = divf.getElementsByClass("style_1").not("[class~=filler]");
				for(Element card: cards) {
					Scraper sc = new Scraper();
					sc.setImgSrc("https://www.themoviedb.org/" + card.select("a[class=image]").select("img").attr("src"));
					sc.setMid(card.select("h2").select("a").attr("href"));
					sc.setTitle(card.select("h2").text());
					shows.add(sc);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return shows;
	}
	
	public Movie movieDetailsTmdb(String id) {//scrapper
		Movie movie = new Movie();
		try {
			Document doc = Jsoup.connect("https://www.themoviedb.org/movie/" + id).get();
			Element divg = doc.getElementById("original_header");
			Element divm = divg.getElementsByClass("header_poster_wrapper").first();
			if(divm==null) {
				movie = null;
			}
			else {
				//String src;
				movie.setTitle(divm.select("h2").select("a").text());
				movie.setRelaseDate(divm.getElementsByClass("release").text());
				Elements genres = divm.getElementsByClass("genres").select("a");
				
				String s = "";
				for(int i=0; i<genres.size();i++) {
					s += genres.get(i).text();
					if(i!=genres.size()-1) s += ", ";
				}
				movie.setGenre(s);
				movie.setDuration(divm.getElementsByClass("runtime").text());
				movie.setImdbid("https://www.themoviedb.org/" + divg.getElementsByClass("image_content").first().select("img").attr("src"));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
	
	public void saveImg(String imgurl, String title) {
		BufferedImage image =null;
        try{
 
            URL url =new URL(imgurl);
           image = ImageIO.read(url);
           
           String[] t2 =title.split(" ");
           String joinedTitle = String.join("", t2);
           List<String> s = Arrays.asList(imgurl.split("\\."));
           String sLast = s.get(s.size()-1);;
           
           if(sLast.equals("png")) {
        	   ImageIO.write(image, "png",new File("C:\\Users\\musta\\Desktop\\springs\\movieP\\src\\main\\resources\\static\\images\\"
        			   + joinedTitle + ".png"));
           }
           if(sLast.equals("jpg")) {
        	   ImageIO.write(image, "jpg",new File("C:\\Users\\musta\\Desktop\\springs\\movieP\\src\\main\\resources\\static\\images\\"
        			   + joinedTitle + ".jpg"));
           }
 
        }catch(IOException e){
            e.printStackTrace();
        }
	}
	
	public Movie movieDetails(String id) {//API
		Movie movie = new Movie();
		try {
			URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "?api_key=a36cbf1c0ff4c82597ebe31432fffda6&language=tr-TR");
			URL imUrl = new URL("https://api.themoviedb.org/3/movie/" + id + "/images?api_key=a36cbf1c0ff4c82597ebe31432fffda6");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			HttpURLConnection con2 = (HttpURLConnection) imUrl.openConnection();
			//int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
			
			String inputLine, input2;
			StringBuffer content = new StringBuffer();
			StringBuffer content2 = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			while ((input2 = in2.readLine()) != null) {
			    content2.append(input2);
			}
			
			in.close();
			in2.close();
			con.disconnect();
			con2.disconnect();
			
			Object obj = new JSONParser().parse(content.toString());
			Object obj2 = new JSONParser().parse(content2.toString());
			JSONObject jo = (JSONObject) obj;
			JSONObject jo2 = (JSONObject) obj2;
			
			JSONArray imgs = (JSONArray) jo2.get("backdrops");
			JSONObject jsonImg = (JSONObject) imgs.get(0);
			
			
			movie.setTitle((String) jo.get("title"));
			movie.setImdbid((String) jo.get("imdb_id"));
			movie.setRelaseDate((String) jo.get("release_date"));
			movie.setImgSource("https://image.tmdb.org/t/p/w300" + (String) jo.get("poster_path"));
			movie.setBackImg("https://image.tmdb.org/t/p/w1280" + jsonImg.get("file_path").toString());
			movie.setSummary((String) jo.get("overview"));
			movie.setScore((double) jo.get("vote_average"));
			
	        int time = Integer.parseInt(jo.get("runtime").toString());
	        int h = 0, m=0;
	        h = time/60;
	        m = time - h*60;
	        String duration = h + "h " + m + "m";
	        movie.setDuration(duration);
	        
	        JSONArray genres = (JSONArray) jo.get("genres");
	        List<String> genreList = new ArrayList<String>();
	          
	        for(int i=0;i<genres.size();i++) {
	        	JSONObject jsonMovie = (JSONObject) genres.get(i);
                genreList.add(jsonMovie.get("name").toString());
	        }

	        String genre = String.join(",", genreList);
	        movie.setGenre(genre);
	        
	        /*System.out.println("genres: " + movie.getGenre());
	        System.out.println("title: " + movie.getTitle());
	        System.out.println("id: " + movie.getImdbid());
	        System.out.println("date: " + movie.getRelaseDate());
	        System.out.println("img: " + movie.getImgSource());
	        System.out.println("özet: " + movie.getSummary());
	        System.out.println("süre: " + movie.getDuration());
	        System.out.println("puan: " + movie.getScore());*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
	
	
	public List<Movie> popMovies(){
		List<Movie> movieList = new ArrayList<Movie>();
		try {
			URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=a36cbf1c0ff4c82597ebe31432fffda6&language=tr-TR&page=1");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			con.disconnect();
			
			Object obj = new JSONParser().parse(content.toString());
			JSONObject jo = (JSONObject) obj;
			
			JSONArray ja = (JSONArray) jo.get("results");
			
			for (int i = 0; i < ja.size(); i++) {
			    JSONObject jsonMovie = (JSONObject) ja.get(i);
		        Movie movie = new Movie();
	            movie.setTitle((String) jsonMovie.get("title"));
				movie.setImdbid(jsonMovie.get("id").toString());
				movie.setRelaseDate((String) jsonMovie.get("release_date"));
				movie.setImgSource("https://image.tmdb.org/t/p/w300" + (String) jsonMovie.get("poster_path"));
				movie.setScore(((Number) jsonMovie.get("vote_average")).doubleValue());
				movieList.add(movie);
			}
			
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movieList;
	}
	
	public List<Movie> searchMovies(String word){
		List<Movie> movieList = new ArrayList<Movie>();
		
		try {
			
			URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=a36cbf1c0ff4c82597ebe31432fffda6&query="+ word +"&page=1&include_adult=false");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			
			Object obj = new JSONParser().parse(content.toString());
			JSONObject jo = (JSONObject) obj;
			JSONArray ja = (JSONArray) jo.get("results");
			
			for(int i=0;i<ja.size();i++) {
				JSONObject jMovie = (JSONObject) ja.get(i);
				Movie movie = new Movie();
				movie.setTitle((String) jMovie.get("title"));
				movie.setImgSource("https://image.tmdb.org/t/p/w300" + (String) jMovie.get("poster_path"));
				movie.setSummary((String) jMovie.get("overview"));
				movie.setImdbid(jMovie.get("id").toString());
				movieList.add(movie);
			}
			
			/*for(int i=0;i<movieList.size();i++) {
				System.out.println(movieList.get(i).getTitle());
				System.out.println(movieList.get(i).getImgSource());
			}*/
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return movieList;
	}
	
	public String movieTrailer(String id) {
		String link = "";
		try {
		
			URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=a36cbf1c0ff4c82597ebe31432fffda6&language=tr-TR");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			
			Object obj = new JSONParser().parse(content.toString());
			JSONObject jo = (JSONObject) obj;
			JSONArray ja = (JSONArray) jo.get("results");
			
			JSONObject js = (JSONObject) ja.get(0);
			link = (String) js.get("key"); 
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return link;
	}
	
	public List<Show> popShows(){
		//https://api.themoviedb.org/3/tv/popular?api_key=a36cbf1c0ff4c82597ebe31432fffda6&language=tr-TR&page=1
		List<Show> showList = new ArrayList<Show>();
		try {
			URL url = new URL("https://api.themoviedb.org/3/tv/popular?api_key=a36cbf1c0ff4c82597ebe31432fffda6&language=tr-TR&page=1");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			con.disconnect();
			
			Object obj = new JSONParser().parse(content.toString());
			JSONObject jo = (JSONObject) obj;
			
			JSONArray ja = (JSONArray) jo.get("results");
			
			for (int i = 0; i < ja.size(); i++) {
			    JSONObject jsonShow = (JSONObject) ja.get(i);
		        Show show = new Show();
		        show.setTmdbId(String.valueOf(jsonShow.get("id")));
		        show.setTitle((String) jsonShow.get("name"));
		        show.setScore(((Number) jsonShow.get("vote_average")).doubleValue());
		        show.setAirDate((String) jsonShow.get("first_air_date"));
		        if(jsonShow.get("poster_path") == null) continue;
		        show.setImgSrc("https://image.tmdb.org/t/p/w300" + (String) jsonShow.get("poster_path"));
		        showList.add(show);
			}
			
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return showList;
	}
	
	HashMap<String, String> genres = new HashMap<String, String>();
	//genres.put("16", "Aksiyon & Macera");
	
	public Show showDetails(String id) {
		Show show = new Show();
		try {
			URL url = new URL("https://api.themoviedb.org/3/tv/" + id + "?api_key=a36cbf1c0ff4c82597ebe31432fffda6&language=tr-TR");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String inputLine;
			StringBuffer content = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			
			in.close();
			con.disconnect();
			
			Object obj = new JSONParser().parse(content.toString());
			JSONObject jo = (JSONObject) obj;
			
			show.setTitle((String) jo.get("name"));
			show.setTmdbId(String.valueOf(jo.get("id")));
			show.setAirDate((String) jo.get("first_air_date"));
			show.setImgSrc("https://image.tmdb.org/t/p/w300" + (String) jo.get("poster_path"));
			show.setImgBack("https://image.tmdb.org/t/p/w1280" + jo.get("backdrop_path").toString());
			show.setSummary((String) jo.get("overview"));
			show.setScore((double) jo.get("vote_average"));
			
	        
	        JSONArray genres = (JSONArray) jo.get("genres");
	        List<String> genreList = new ArrayList<String>();
	          
	        for(int i=0;i<genres.size();i++) {
	        	JSONObject jsonShow = (JSONObject) genres.get(i);
                genreList.add(jsonShow.get("name").toString());
	        }

	        String genre = String.join(",", genreList);
	        show.setGenre(genre);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return show;
	}
	
	public String showTrailer(String id) {
		String link = "";
		try {
		
			URL url = new URL("https://api.themoviedb.org/3/tv/" + id + "/videos?api_key=a36cbf1c0ff4c82597ebe31432fffda6&language=tr-TR");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			
			Object obj = new JSONParser().parse(content.toString());
			JSONObject jo = (JSONObject) obj;
			JSONArray ja = (JSONArray) jo.get("results");
			
			JSONObject js = (JSONObject) ja.get(0);
			link = (String) js.get("key"); 
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return link;
	}

	/*public static void main(String[] args) {

		searchMovies("doctor");
		
	}*/

}














import express, { Router, Request, Response } from 'express';
import axios from 'axios';
import Favorite from '../models/Favorite';
import NodeCache from 'node-cache';

const router: Router = express.Router();
const cache = new NodeCache({ stdTTL: 600 }); // 10 minutes cache

const EXTERNAL_APIs = {
  JOKE_API: 'https://v2.jokeapi.dev/joke',
  OFFICIAL_JOKE_API: 'https://official-joke-api.appspot.com/jokes',
};

// Get Random Joke
router.get('/random', async (req: Request, res: Response) => {
  try {
    const cacheKey = 'random_joke';
    const cachedJoke = cache.get(cacheKey);

    if (cachedJoke) {
      return res.json(cachedJoke);
    }

    // Try JokeAPI first
    try {
      const response = await axios.get(`${EXTERNAL_APIs.JOKE_API}/Any`, {
        params: {
          format: 'json',
          safe: true,
        },
      });

      if (response.data && !response.data.error) {
        cache.set(cacheKey, response.data);
        return res.json(response.data);
      }
    } catch (error) {
      console.log('JokeAPI failed, trying fallback...');
    }

    // Fallback to Official Joke API
    const fallbackResponse = await axios.get(`${EXTERNAL_APIs.OFFICIAL_JOKE_API}/random`);
    cache.set(cacheKey, fallbackResponse.data);
    res.json(fallbackResponse.data);
  } catch (error) {
    res.status(500).json({ 
      error: true, 
      message: 'Failed to fetch joke',
      details: (error as any).message 
    });
  }
});

// Get Joke by Category
router.get('/category/:category', async (req: Request, res: Response) => {
  try {
    const { category } = req.params;
    const cacheKey = `joke_${category}`;
    const cachedJoke = cache.get(cacheKey);

    if (cachedJoke) {
      return res.json(cachedJoke);
    }

    const response = await axios.get(
      `${EXTERNAL_APIs.JOKE_API}/${category}`,
      {
        params: {
          format: 'json',
          safe: true,
        },
      }
    );

    if (response.data && !response.data.error) {
      cache.set(cacheKey, response.data);
      return res.json(response.data);
    }

    res.status(404).json({ error: true, message: 'Joke not found' });
  } catch (error) {
    res.status(500).json({ 
      error: true, 
      message: 'Failed to fetch joke by category',
      details: (error as any).message 
    });
  }
});

// Search Jokes
router.get('/search', async (req: Request, res: Response) => {
  try {
    const { q } = req.query as { q: string };

    if (!q || q.trim() === '') {
      return res.status(400).json({ error: true, message: 'Search query required' });
    }

    const cacheKey = `search_${q}`;
    const cachedResults = cache.get(cacheKey);

    if (cachedResults) {
      return res.json(cachedResults);
    }

    // Fetch multiple jokes and filter based on search query
    const jokes = [];
    for (let i = 0; i < 5; i++) {
      try {
        const response = await axios.get(`${EXTERNAL_APIs.JOKE_API}/Any`);
        if (response.data && !response.data.error) {
          const jokeText = response.data.joke || 
                          `${response.data.setup} ${response.data.delivery}`;
          
          if (jokeText.toLowerCase().includes(q.toLowerCase())) {
            jokes.push(response.data);
          }
        }
      } catch (e) {
        // Continue to next iteration
      }
    }

    const result = { jokes, total: jokes.length };
    cache.set(cacheKey, result);
    res.json(result);
  } catch (error) {
    res.status(500).json({ 
      error: true, 
      message: 'Search failed',
      details: (error as any).message 
    });
  }
});

// Add to Favorites
router.post('/favorites', async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    const { joke, setup, delivery, type } = req.body;

    const favorite = new Favorite({
      userId,
      joke,
      setup,
      delivery,
      type,
    });

    await favorite.save();
    res.status(201).json(favorite);
  } catch (error) {
    res.status(500).json({ 
      error: true, 
      message: 'Failed to add favorite',
      details: (error as any).message 
    });
  }
});

// Get User's Favorites
router.get('/favorites', async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    const favorites = await Favorite.find({ userId }).sort({ createdAt: -1 });

    res.json({
      favorites,
      total: favorites.length,
    });
  } catch (error) {
    res.status(500).json({ 
      error: true, 
      message: 'Failed to fetch favorites',
      details: (error as any).message 
    });
  }
});

// Remove Favorite
router.delete('/favorites/:id', async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    const { id } = req.params;

    await Favorite.findByIdAndDelete(id);
    res.json({ success: true, message: 'Favorite removed' });
  } catch (error) {
    res.status(500).json({ 
      error: true, 
      message: 'Failed to remove favorite',
      details: (error as any).message 
    });
  }
});

export default router;

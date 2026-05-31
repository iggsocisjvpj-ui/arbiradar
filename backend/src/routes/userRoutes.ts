import express, { Router, Request, Response } from 'express';
import User from '../models/User';

const router: Router = express.Router();

// Get User Profile
router.get('/profile', async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    const user = await User.findById(userId).select('-password');
    
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    res.json(user);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
});

// Update User Profile
router.put('/profile', async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    const { name, phone, avatar } = req.body;

    const user = await User.findByIdAndUpdate(
      userId,
      { name, phone, avatar },
      { new: true }
    ).select('-password');

    res.json(user);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
});

export default router;
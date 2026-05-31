import express, { Router, Request, Response } from 'express';
import Data from '../models/Data';

const router: Router = express.Router();

// Get All Data
router.get('/list', async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    const page = parseInt(req.query.page as string) || 1;
    const limit = 10;
    const skip = (page - 1) * limit;

    const data = await Data.find({ userId })
      .limit(limit)
      .skip(skip)
      .sort({ createdAt: -1 });

    const total = await Data.countDocuments({ userId });

    res.json({
      data,
      total,
      page,
      pages: Math.ceil(total / limit),
    });
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
});

// Get Single Data
router.get('/:id', async (req: Request, res: Response) => {
  try {
    const data = await Data.findById(req.params.id);
    if (!data) {
      return res.status(404).json({ message: 'Data not found' });
    }
    res.json(data);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
});

// Create Data
router.post('/create', async (req: Request, res: Response) => {
  try {
    const userId = (req as any).userId;
    const { title, description, content } = req.body;

    const data = new Data({ userId, title, description, content });
    await data.save();

    res.status(201).json(data);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
});

// Update Data
router.put('/:id', async (req: Request, res: Response) => {
  try {
    const { title, description, content } = req.body;
    const data = await Data.findByIdAndUpdate(
      req.params.id,
      { title, description, content },
      { new: true }
    );

    res.json(data);
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
});

// Delete Data
router.delete('/:id', async (req: Request, res: Response) => {
  try {
    await Data.findByIdAndDelete(req.params.id);
    res.json({ success: true, message: 'Data deleted successfully' });
  } catch (error) {
    res.status(500).json({ message: 'Server error', error });
  }
});

export default router;
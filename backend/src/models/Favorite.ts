import mongoose, { Schema, Document } from 'mongoose';

interface IFavorite extends Document {
  userId: string;
  joke: string;
  setup?: string;
  delivery?: string;
  type: string;
  createdAt: Date;
  updatedAt: Date;
}

const FavoriteSchema = new Schema<IFavorite>(
  {
    userId: {
      type: String,
      required: true,
    },
    joke: {
      type: String,
      required: true,
    },
    setup: {
      type: String,
      default: null,
    },
    delivery: {
      type: String,
      default: null,
    },
    type: {
      type: String,
      default: 'single',
    },
  },
  {
    timestamps: true,
  }
);

// Index for faster queries
FavoriteSchema.index({ userId: 1, createdAt: -1 });

export default mongoose.model<IFavorite>('Favorite', FavoriteSchema);

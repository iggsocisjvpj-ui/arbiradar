import mongoose, { Schema, Document } from 'mongoose';

interface IData extends Document {
  userId: string;
  title: string;
  description: string;
  content?: string;
  createdAt: Date;
  updatedAt: Date;
}

const DataSchema = new Schema<IData>(
  {
    userId: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    description: {
      type: String,
      required: true,
    },
    content: {
      type: String,
      default: null,
    },
  },
  {
    timestamps: true,
  }
);

export default mongoose.model<IData>('Data', DataSchema);
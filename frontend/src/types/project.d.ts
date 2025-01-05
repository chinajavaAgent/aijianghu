export interface Project {
  id: number;
  title: string;
  price: number;
  introduction: string;
  coverImage: string;
  keyPoints: string[];
  examples: {
    title: string;
    image: string;
    description: string;
    income: string;
  }[];
  videos: {
    title: string;
    cover: string;
    duration: string;
    videoUrl: string;
  }[];
  benefits: string[];
} 
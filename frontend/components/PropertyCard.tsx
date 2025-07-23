import React from 'react';

import { Badge } from "@/components/ui/badge";
import { MapPin, Bed, Bath, ArrowRight, Home } from "lucide-react";
import Link from 'next/link';

interface PropertyCardProps {
    id: string;
    title: string;
    location: string;
    price: number;
    image: string;
    type: string;
    bedrooms: number;
    bathrooms: number;
    area: number;
}

const PropertyCard = ({
    id,
    title,
    location,
    price,
    image,
    type,
    bedrooms,
    bathrooms,
    area
}: PropertyCardProps) => {
    return (
        <div className="property-card group">
            <div className="relative overflow-hidden h-48">
                <img
                    src={image}
                    alt={title}
                    className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
                />
                <Badge className="absolute top-2 left-2 bg-[#e56b4e]">{type}</Badge>
            </div>
            <div className="p-4">
                <div className="flex items-start justify-between">
                    <div>
                        <h3 className="font-heading font-medium text-lg line-clamp-1">{title}</h3>
                        <div className="flex items-center mt-1 text-muted-foreground text-sm">
                            <MapPin className="h-3.5 w-3.5 mr-1" />
                            <span className="line-clamp-1">{location}</span>
                        </div>
                    </div>
                    <p className="text-[#e56b4e] font-heading font-medium">
                        R${price.toLocaleString('pt-BR')}
                    </p>
                </div>

                <div className="flex items-center justify-between mt-4 pt-4 border-t">
                    <div className="flex items-center gap-3">
                        <div className="flex items-center text-muted-foreground">
                            <Bed className="h-4 w-4 mr-1" />
                            <span className="text-xs">{bedrooms}</span>
                        </div>
                        <div className="flex items-center text-muted-foreground">
                            <Bath className="h-4 w-4 mr-1" />
                            <span className="text-xs">{bathrooms}</span>
                        </div>
                        <div className="flex items-center text-muted-foreground">
                            <Home className="h-4 w-4 mr-1" />
                            <span className="text-xs">{area} m²</span>
                        </div>
                    </div>

                    <Link
                        href={`/properties/${id}`}
                        className="text-[#e56b4e] text-sm flex items-center font-medium hover:underline"
                    >
                        Detalhes
                        <ArrowRight className="ml-1 h-3.5 w-3.5" />
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default PropertyCard;
import React, { useState } from 'react';
import { Slider } from "@/components/ui/slider";
import { Checkbox } from "@/components/ui/checkbox";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { SlidersHorizontal, ArrowLeft, ArrowRight } from "lucide-react";
import { cn } from "@/lib/utils";
import { useIsMobile } from '@/hooks/use-mobile';

interface PropertySidebarProps {
    className?: string;
}

const PropertySidebar = ({ className }: PropertySidebarProps) => {
    const [isCollapsed, setIsCollapsed] = useState(false);
    const [priceRange, setPriceRange] = useState<number[]>([500, 5000]);
    const [bedRooms, setBedRooms] = useState<number>(0);
    const [bathRooms, setBathRooms] = useState<number>(0);

    const isMobile = useIsMobile();

    const styleOfSelectedButton = { backgroundColor: "#e56b4e", color: "white" };

    const handlePriceChange = (value: number[]) => {
        setPriceRange(value);
    };

    const toggleSidebar = () => {
        setIsCollapsed(!isCollapsed);
    };

    const toogleFilterBedRooms = (value: number) => {
        if (bedRooms === value) {
            setBedRooms(0);
        } else {
            setBedRooms(value);
        }
    };

    const toogleFilterBathRooms = (value: number) => {
        if (bedRooms === value) {
            setBedRooms(0);
        } else {
            setBedRooms(value);
        }
    };


    return (
        <div className={cn(
            "bg-white border-r transition-all duration-300 relative",
            isCollapsed ? "w-12" : "w-full md:w-64 lg:w-80",
            className
        )}>
            <Button
                variant="ghost"
                size="icon"
                onClick={toggleSidebar}
                className="absolute -right-4 top-4 h-8 w-8 rounded-full border bg-white shadow-sm md:flex"
            >
                {isCollapsed ? <ArrowRight className="h-4 w-4" /> : <ArrowLeft className="h-4 w-4" />}
            </Button>

            {!isCollapsed && (
                <div className="p-4 space-y-6">
                    <div>
                        <h3 className="font-heading font-medium text-lg mb-4">Filtros</h3>

                        <div className="space-y-4">
                            <div>
                                <Label htmlFor="location" className="text-sm font-medium">Localização</Label>
                                <Input
                                    id="location"
                                    placeholder="Cidade, bairro ou endereço"
                                    className="mt-1"
                                />
                            </div>

                            <div>
                                <Label htmlFor="property-type" className="text-sm font-medium">Tipo de Imóvel</Label>
                                <Select>
                                    <SelectTrigger className="mt-1">
                                        <SelectValue placeholder="Todos os tipos" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectItem value="all">Todos os tipos</SelectItem>
                                        <SelectItem value="house">Casa</SelectItem>
                                        <SelectItem value="apartment">Apartamento</SelectItem>
                                        <SelectItem value="studio">Studio</SelectItem>
                                        <SelectItem value="land">Terreno</SelectItem>
                                        <SelectItem value="commercial">Comercial</SelectItem>
                                    </SelectContent>
                                </Select>
                            </div>

                            <div>
                                <div className="flex items-center justify-between mb-1">
                                    <Label className="text-sm font-medium">Faixa de Preço</Label>
                                    <span className="text-sm text-muted-foreground">
                                        R${priceRange[0]} - R${priceRange[1]}
                                    </span>
                                </div>
                                <Slider
                                    defaultValue={[500, 5000]}
                                    min={0}
                                    max={10000}
                                    step={100}
                                    value={priceRange}
                                    onValueChange={handlePriceChange}
                                    className="mt-2"
                                    color='#e56b4e'

                                />
                            </div>

                            <div>
                                <Label className="text-sm font-medium mb-2">Comodidades</Label>
                                <div className="grid grid-cols-2 gap-2 mt-1">
                                    <div className="flex items-center space-x-2">
                                        <Checkbox id="pool" />
                                        <label htmlFor="pool" className="text-sm">Piscina</label>
                                    </div>
                                    <div className="flex items-center space-x-2">
                                        <Checkbox id="garage" />
                                        <label htmlFor="garage" className="text-sm">Garagem</label>
                                    </div>
                                    <div className="flex items-center space-x-2">
                                        <Checkbox id="garden" />
                                        <label htmlFor="garden" className="text-sm">Jardim</label>
                                    </div>
                                    <div className="flex items-center space-x-2">
                                        <Checkbox id="security" />
                                        <label htmlFor="security" className="text-sm">Segurança</label>
                                    </div>
                                    <div className="flex items-center space-x-2">
                                        <Checkbox id="furnished" />
                                        <label htmlFor="furnished" className="text-sm">Mobiliado</label>
                                    </div>
                                    <div className="flex items-center space-x-2">
                                        <Checkbox id="pets" />
                                        <label htmlFor="pets" className="text-sm">Pet Friendly</label>
                                    </div>
                                </div>
                            </div>

                            <div>
                                <Label className="text-sm font-medium mb-2">Dormitórios</Label>
                                <div className="grid grid-cols-4 gap-2 mt-1">
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bedRooms === 1 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBedRooms(1)}>
                                        1+
                                    </Button>
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bedRooms === 2 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBedRooms(2)}>
                                        2+
                                    </Button>
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bedRooms === 3 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBedRooms(3)}>
                                        3+
                                    </Button>
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bedRooms === 4 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBedRooms(4)}>
                                        4+
                                    </Button>
                                </div>
                            </div>

                            <div>
                                <Label className="text-sm font-medium mb-2">Banheiros</Label>
                                <div className="grid grid-cols-4 gap-2 mt-1">
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bathRooms === 1 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBathRooms(1)}>
                                        1+
                                    </Button>
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bathRooms === 2 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBathRooms(2)}>
                                        2+
                                    </Button>
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bathRooms === 3 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBathRooms(3)}>
                                        3+
                                    </Button>
                                    <Button variant="outline" size="sm" className="hover:bg-[#e56b4e] hover:text-white" style={bathRooms === 4 ? styleOfSelectedButton : {}} onClick={() => toogleFilterBathRooms(4)}>

                                        4+
                                    </Button>
                                </div>
                            </div>

                            <Button className="w-full bg-[#e56b4e] text-white hover:bg-[#e56b4e]/90">
                                Aplicar Filtros
                            </Button>

                            <Button variant="outline" className="w-full">
                                Limpar Filtros
                            </Button>
                        </div>
                    </div>
                </div>
            )}

            {isCollapsed && (
                <div className="flex flex-col items-center justify-center pt-12">
                    <SlidersHorizontal className="h-5 w-5 text-muted-foreground mb-6" />
                    <span className="text-muted-foreground text-xs vertical-text">FILTROS</span>
                </div>
            )}
        </div>
    );
};

export default PropertySidebar;
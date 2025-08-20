'use client';

import { useState } from "react";
import MainLayout from "@/components/layout/MainLayout";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { MapPin, Bed, Bath, Home as HomeIcon, ArrowLeft, Key, Car, Ruler, UtensilsCrossed, DoorOpen, ShowerHead, Save } from "lucide-react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { toast } from "@/hooks/use-toast";
import { useParams } from "next/navigation";
import Link from "next/link";

// Dados estáticos para demonstração (mesmo do PropertyDetails)
export interface IProperties {
    id: string,
    title: string,
    description: string,
    location: string,
    price: number,
    image: string,
    type: string,
    bedrooms: number,
    bathrooms: number,
    area: number,
    features: { name: string, type: string; }[],
    nearbyAmenities: { name: string, distance: string; }[],
    additionalImages: string[];
}

interface EditPropertiesProps {
    properties: IProperties[];
}

const EditPropertyPage: React.FC<EditPropertiesProps> = ({ properties }) => {
    const { id } = useParams();

    // Estado para controlar se está em modo de edição
    const [isEditing, setIsEditing] = useState(false);
    const [isSaving, setIsSaving] = useState(false);

    // Dados da propriedade (seria carregado de uma API)
    const property = properties.find(p => p.id === id) || properties[0];

    // Estados para os campos editáveis
    const [editedProperty, setEditedProperty] = useState({
        title: property.title,
        description: property.description,
        price: property.price,
        location: property.location,
        bedrooms: property.bedrooms,
        bathrooms: property.bathrooms,
        area: property.area,
        type: property.type
    });

    const handleSave = async () => {
        setIsSaving(true);

        // Simular salvamento
        await new Promise(resolve => setTimeout(resolve, 1000));

        console.log('Propriedade atualizada:', editedProperty);

        toast({
            title: "Propriedade atualizada!",
            description: "As alterações foram salvas com sucesso.",
        });

        setIsSaving(false);
        setIsEditing(false);
    };

    return (
        <MainLayout>
            <div className="container px-4 py-8 mx-auto max-w-7xl">
                <div className="mb-6">
                    <Link
                        href="/my-properties"
                        className="text-muted-foreground flex items-center hover:text-[#e56b4e] transition-colors mb-4"
                    >
                        <ArrowLeft className="h-4 w-4 mr-1" />
                        Voltar para minhas propriedades
                    </Link>

                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
                        <div className="flex-1">
                            {isEditing ? (
                                <div className="space-y-2">
                                    <Input
                                        value={editedProperty.title}
                                        onChange={(e) => setEditedProperty({ ...editedProperty, title: e.target.value })}
                                        className="text-2xl md:text-3xl font-bold border-dashed"
                                    />
                                    <Input
                                        value={editedProperty.location}
                                        onChange={(e) => setEditedProperty({ ...editedProperty, location: e.target.value })}
                                        className="text-muted-foreground border-dashed"
                                    />
                                </div>
                            ) : (
                                <div>
                                    <h1 className="font-heading text-2xl md:text-3xl font-bold">{editedProperty.title}</h1>
                                    <div className="flex items-center mt-1 text-muted-foreground">
                                        <MapPin className="h-4 w-4 mr-1" />
                                        <span>{editedProperty.location}</span>
                                    </div>
                                </div>
                            )}
                        </div>

                        <div className="flex items-center gap-3">
                            <div className="flex flex-col items-end">
                                {isEditing ? (
                                    <Input
                                        type="number"
                                        value={editedProperty.price}
                                        onChange={(e) => setEditedProperty({ ...editedProperty, price: Number(e.target.value) })}
                                        className="text-right text-2xl font-medium text-[#e56b4e] border-dashed w-40"
                                    />
                                ) : (
                                    <p className="text-[#e56b4e] font-heading text-2xl font-medium">
                                        R${editedProperty.price.toLocaleString('pt-BR')}
                                    </p>
                                )}
                                <span className="text-sm text-muted-foreground">por mês</span>
                            </div>

                            {isEditing ? (
                                <div className="flex gap-2">
                                    <Button variant="outline" onClick={() => setIsEditing(false)}>
                                        Cancelar
                                    </Button>
                                    <Button
                                        onClick={handleSave}
                                        disabled={isSaving}
                                        className="bg-[#e56b4e] hover:bg-[#e56b4e]/90"
                                    >
                                        <Save className="h-4 w-4 mr-2" />
                                        {isSaving ? 'Salvando...' : 'Salvar'}
                                    </Button>
                                </div>
                            ) : (
                                <Button
                                    onClick={() => setIsEditing(true)}
                                    className="bg-[#e56b4e] hover:bg-[#e56b4e]/90"
                                >
                                    Editar
                                </Button>
                            )}
                        </div>
                    </div>
                </div>

                {/* Galeria de imagens */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
                    <div className="md:col-span-2 h-96 relative">
                        <img
                            src={property.image}
                            alt={property.title}
                            className="w-full h-full object-cover rounded-lg"
                        />
                        <Badge className="absolute top-4 left-4 bg-[#e56b4e]">{editedProperty.type}</Badge>
                    </div>

                    <div className="grid grid-cols-2 gap-4 h-96">
                        {property.additionalImages?.slice(0, 4).map((img, index) => (
                            <div key={img} className="relative h-[calc(50%-0.5rem)] overflow-hidden rounded-lg">
                                <img
                                    src={img}
                                    alt={`${property.title} - imagem ${index + 2}`}
                                    className="w-full h-full object-cover"
                                />
                            </div>
                        ))}
                    </div>
                </div>

                {/* Informações principais */}
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-8">
                    <div className="lg:col-span-2">
                        <Tabs defaultValue="detalhes" className="w-full">
                            <TabsList className="grid grid-cols-3 mb-6">
                                <TabsTrigger value="detalhes">Detalhes</TabsTrigger>
                                <TabsTrigger value="caracteristicas">Características</TabsTrigger>
                                <TabsTrigger value="localizacao">Localização</TabsTrigger>
                            </TabsList>

                            <TabsContent value="detalhes" className="mt-0">
                                <div className="prose max-w-none">
                                    <h3 className="font-heading text-xl font-medium mb-4">Descrição</h3>
                                    {isEditing ? (
                                        <textarea
                                            value={editedProperty.description}
                                            onChange={(e) => setEditedProperty({ ...editedProperty, description: e.target.value })}
                                            className="w-full min-h-[100px] p-3 border border-dashed rounded-md"
                                        />
                                    ) : (
                                        <p className="text-muted-foreground">{editedProperty.description}</p>
                                    )}

                                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 my-8">
                                        <Card className="border-0 bg-muted/30">
                                            <CardContent className="p-4 flex flex-col items-center justify-center">
                                                <Bed className="h-6 w-6 text-[#e56b4e] mb-2" />
                                                {isEditing ? (
                                                    <Input
                                                        type="number"
                                                        value={editedProperty.bedrooms}
                                                        onChange={(e) => setEditedProperty({ ...editedProperty, bedrooms: Number(e.target.value) })}
                                                        className="w-16 text-center border-dashed"
                                                    />
                                                ) : (
                                                    <p className="text-sm font-medium">{editedProperty.bedrooms} Quartos</p>
                                                )}
                                            </CardContent>
                                        </Card>
                                        <Card className="border-0 bg-muted/30">
                                            <CardContent className="p-4 flex flex-col items-center justify-center">
                                                <Bath className="h-6 w-6 text-[#e56b4e] mb-2" />
                                                {isEditing ? (
                                                    <Input
                                                        type="number"
                                                        value={editedProperty.bathrooms}
                                                        onChange={(e) => setEditedProperty({ ...editedProperty, bathrooms: Number(e.target.value) })}
                                                        className="w-16 text-center border-dashed"
                                                    />
                                                ) : (
                                                    <p className="text-sm font-medium">{editedProperty.bathrooms} Banheiros</p>
                                                )}
                                            </CardContent>
                                        </Card>
                                        <Card className="border-0 bg-muted/30">
                                            <CardContent className="p-4 flex flex-col items-center justify-center">
                                                <HomeIcon className="h-6 w-6 text-[#e56b4e] mb-2" />
                                                {isEditing ? (
                                                    <Input
                                                        type="number"
                                                        value={editedProperty.area}
                                                        onChange={(e) => setEditedProperty({ ...editedProperty, area: Number(e.target.value) })}
                                                        className="w-16 text-center border-dashed"
                                                    />
                                                ) : (
                                                    <p className="text-sm font-medium">{editedProperty.area} m²</p>
                                                )}
                                            </CardContent>
                                        </Card>
                                        <Card className="border-0 bg-muted/30">
                                            <CardContent className="p-4 flex flex-col items-center justify-center">
                                                <Key className="h-6 w-6 text-[#e56b4e] mb-2" />
                                                <p className="text-sm font-medium">Disponível</p>
                                            </CardContent>
                                        </Card>
                                    </div>
                                </div>
                            </TabsContent>

                            <TabsContent value="caracteristicas" className="mt-0">
                                <div>
                                    <h3 className="font-heading text-xl font-medium mb-4">Comodidades e Características</h3>

                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-2 mb-6">
                                        {property.features?.map((feature, index) => (
                                            <div key={feature.name} className="flex items-center py-2">
                                                <div className="w-9 h-9 rounded-full bg-muted/50 flex items-center justify-center mr-3">
                                                    {getFeatureIcon(feature.type)}
                                                </div>
                                                <span>{feature.name}</span>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </TabsContent>

                            <TabsContent value="localizacao" className="mt-0">
                                <h3 className="font-heading text-xl font-medium mb-4">Localização</h3>
                                <div className="aspect-video bg-muted mb-6 rounded-lg flex items-center justify-center">
                                    <p className="text-muted-foreground">Mapa de localização do imóvel</p>
                                </div>

                                <h4 className="font-heading text-lg font-medium mb-3">Proximidades</h4>
                                <Table>
                                    <TableHeader>
                                        <TableRow>
                                            <TableHead>Local</TableHead>
                                            <TableHead>Distância</TableHead>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody>
                                        {property.nearbyAmenities?.map((place, index) => (
                                            <TableRow key={place.name}>
                                                <TableCell>{place.name}</TableCell>
                                                <TableCell>{place.distance}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TabsContent>
                        </Tabs>
                    </div>

                    <div>
                        <Card>
                            <CardContent className="p-6">
                                <h3 className="font-heading text-lg font-medium mb-4">Ações da Propriedade</h3>
                                <p className="text-sm text-muted-foreground mb-6">
                                    Gerencie sua propriedade com as opções disponíveis abaixo.
                                </p>

                                <div className="grid gap-4">
                                    <Button className="bg-[#e56b4e] hover:bg-[#e56b4e]/90 w-full">
                                        Impulsionar anúncio
                                    </Button>
                                    <Button variant="outline" className="w-full">
                                        Ver como inquilino
                                    </Button>
                                    <Button variant="ghost" className="w-full">
                                        Gerenciar propostas
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

// Função auxiliar para exibir ícones baseados no tipo de característica
const getFeatureIcon = (type: string) => {
    switch (type) {
        case 'garage':
            return <Car className="h-5 w-5 text-[#e56b4e]" />;
        case 'area':
            return <Ruler className="h-5 w-5 text-[#e56b4e]" />;
        case 'kitchen':
            return <UtensilsCrossed className="h-5 w-5 text-[#e56b4e]" />;
        case 'bathroom':
            return <ShowerHead className="h-5 w-5 text-[#e56b4e]" />;
        case 'bedroom':
            return <Bed className="h-5 w-5 text-[#e56b4e]" />;
        case 'livingroom':
            return <DoorOpen className="h-5 w-5 text-[#e56b4e]" />;
        default:
            return <HomeIcon className="h-5 w-5 text-[#e56b4e]" />;
    }
};

export default EditPropertyPage;
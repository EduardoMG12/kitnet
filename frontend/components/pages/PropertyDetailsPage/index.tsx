'use client';

import MainLayout from "@/components/layout/MainLayout";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { MapPin, Bed, Bath, Home as HomeIcon, ArrowLeft, Key, Car, Ruler, UtensilsCrossed, DoorOpen, ShowerHead, Star, User, Phone, MessageCircle } from "lucide-react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { useParams } from "next/navigation";
import Link from "next/link";
import type { IProperties } from "../EditPropertyPage";

interface PropertyDetailsProps {
    properties: IProperties[];
}

const PropertyDetailsPage: React.FC<PropertyDetailsProps> = ({ properties }) => {
    const { id } = useParams();

    // Na implementação real, você buscaria os dados do imóvel de uma API
    // Aqui usamos dados estáticos simulados
    const property = properties.find(p => p.id === id) || {
        id: '0',
        title: 'Imóvel não encontrado',
        description: 'Não foi possível encontrar os detalhes deste imóvel',
        location: '',
        price: 0,
        image: 'https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=1200&q=80',
        type: '',
        bedrooms: 0,
        bathrooms: 0,
        area: 0,
        features: [],
        nearbyAmenities: [],
        additionalImages: []
    };

    // Mock data para corretor e proprietário
    const propertyAgent = {
        id: '1',
        name: 'João Silva',
        role: 'Corretor',
        creci: '12345-F',
        rating: 4.8,
        reviewsCount: 127,
        phone: '(11) 99999-9999',
        avatar: '/placeholder-avatar.jpg',
        propertiesCount: 24
    };

    const propertyOwner = {
        id: '2',
        name: 'Maria Santos',
        role: 'Proprietária',
        rating: 4.5,
        reviewsCount: 23,
        phone: '(21) 98888-8888',
        avatar: '/placeholder-avatar.jpg',
        propertiesCount: 6
    };

    return (
        <MainLayout>
            <div className="container px-4 py-8 mx-auto max-w-7xl">
                <div className="mb-6">
                    <Link
                        href="/properties"
                        className="text-muted-foreground flex items-center hover:text-[#e56b4e] transition-colors mb-4"
                    >
                        <ArrowLeft className="h-4 w-4 mr-1" />
                        Voltar para a lista de imóveis
                    </Link>

                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
                        <div>
                            <h1 className="font-heading text-2xl md:text-3xl font-bold">{property.title}</h1>
                            <div className="flex items-center mt-1 text-muted-foreground">
                                <MapPin className="h-4 w-4 mr-1" />
                                <span>{property.location}</span>
                            </div>
                        </div>

                        <div className="flex flex-col items-end">
                            <p className="text-[#e56b4e] font-heading text-2xl font-medium">
                                R${property.price.toLocaleString('pt-BR')}
                            </p>
                            <span className="text-sm text-muted-foreground">por mês</span>
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
                        <Badge className="absolute top-4 left-4 bg-[#e56b4e]">{property.type}</Badge>
                    </div>

                    <div className="grid grid-cols-2 gap-4 h-96">
                        {property.additionalImages?.slice(0, 4).map((img) => (
                            <div key={img} className="relative h-[calc(50%-0.5rem)] overflow-hidden rounded-lg">
                                <img
                                    src={img}
                                    alt={`${property.title} - imagem`}
                                    className="w-full h-full object-cover"
                                />
                            </div>
                        ))}
                    </div>
                </div>

                {/* Informações principais e ações */}
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-8">
                    <div className="lg:col-span-2">
                        <Tabs defaultValue="detalhes" className="w-full">
                            <TabsList className="grid grid-cols-4 mb-6">
                                <TabsTrigger value="detalhes">Detalhes</TabsTrigger>
                                <TabsTrigger value="caracteristicas">Características</TabsTrigger>
                                <TabsTrigger value="localizacao">Localização</TabsTrigger>
                                <TabsTrigger value="responsaveis">Responsáveis</TabsTrigger>
                            </TabsList>

                            <TabsContent value="detalhes" className="mt-0">
                                <div className="prose max-w-none">
                                    <h3 className="font-heading text-xl font-medium mb-4">Descrição</h3>
                                    <p className="text-muted-foreground">{property.description}</p>

                                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 my-8">
                                        <Card className="border-0 bg-muted/30">
                                            <CardContent className="p-4 flex flex-col items-center justify-center">
                                                <Bed className="h-6 w-6 text-[#e56b4e] mb-2" />
                                                <p className="text-sm font-medium">{property.bedrooms} Quartos</p>
                                            </CardContent>
                                        </Card>
                                        <Card className="border-0 bg-muted/30">
                                            <CardContent className="p-4 flex flex-col items-center justify-center">
                                                <Bath className="h-6 w-6 text-[#e56b4e] mb-2" />
                                                <p className="text-sm font-medium">{property.bathrooms} Banheiros</p>
                                            </CardContent>
                                        </Card>
                                        <Card className="border-0 bg-muted/30">
                                            <CardContent className="p-4 flex flex-col items-center justify-center">
                                                <HomeIcon className="h-6 w-6 text-[#e56b4e] mb-2" />
                                                <p className="text-sm font-medium">{property.area} m²</p>
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
                                    {/* Aqui ficaria o mapa com a localização do imóvel */}
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

                            <TabsContent value="responsaveis" className="mt-0">
                                <div className="space-y-6">
                                    <h3 className="font-heading text-xl font-medium mb-4">Responsáveis pelo Imóvel</h3>

                                    {/* Corretor */}
                                    <Card>
                                        <CardContent className="p-6">
                                            <div className="flex items-start gap-4">
                                                <Avatar className="h-16 w-16">
                                                    <AvatarImage src={propertyAgent.avatar} alt={propertyAgent.name} />
                                                    <AvatarFallback className="bg-[#e56b4e] text-white text-lg">
                                                        {propertyAgent.name.split(' ').map(n => n[0]).join('')}
                                                    </AvatarFallback>
                                                </Avatar>

                                                <div className="flex-1">
                                                    <div className="flex items-start justify-between">
                                                        <div>
                                                            <h4 className="font-medium text-lg">{propertyAgent.name}</h4>
                                                            <div className="flex items-center gap-2 mb-2">
                                                                <Badge variant="secondary">{propertyAgent.role}</Badge>
                                                                <Badge className="bg-[#e56b4e]">CRECI: {propertyAgent.creci}</Badge>
                                                            </div>
                                                            <div className="flex items-center gap-1 mb-2">
                                                                <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                                                                <span className="text-sm font-medium">{propertyAgent.rating}</span>
                                                                <span className="text-sm text-muted-foreground">({propertyAgent.reviewsCount} avaliações)</span>
                                                            </div>
                                                            <p className="text-sm text-muted-foreground">
                                                                {propertyAgent.propertiesCount} propriedades anunciadas
                                                            </p>
                                                        </div>

                                                        <div className="flex flex-col gap-2">
                                                            <Link href={`/profile/broker/${propertyAgent.id}`}>
                                                                <Button variant="outline" size="sm">
                                                                    <User className="h-4 w-4 mr-2" />
                                                                    Ver Perfil
                                                                </Button>
                                                            </Link>
                                                            <Button size="sm" className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                                                <MessageCircle className="h-4 w-4 mr-2" />
                                                                Contatar
                                                            </Button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </CardContent>
                                    </Card>

                                    {/* Proprietário */}
                                    <Card>
                                        <CardContent className="p-6">
                                            <div className="flex items-start gap-4">
                                                <Avatar className="h-16 w-16">
                                                    <AvatarImage src={propertyOwner.avatar} alt={propertyOwner.name} />
                                                    <AvatarFallback className="bg-blue-600 text-white text-lg">
                                                        {propertyOwner.name.split(' ').map(n => n[0]).join('')}
                                                    </AvatarFallback>
                                                </Avatar>

                                                <div className="flex-1">
                                                    <div className="flex items-start justify-between">
                                                        <div>
                                                            <h4 className="font-medium text-lg">{propertyOwner.name}</h4>
                                                            <Badge variant="secondary" className="mb-2">{propertyOwner.role}</Badge>
                                                            <div className="flex items-center gap-1 mb-2">
                                                                <Star className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                                                                <span className="text-sm font-medium">{propertyOwner.rating}</span>
                                                                <span className="text-sm text-muted-foreground">({propertyOwner.reviewsCount} avaliações)</span>
                                                            </div>
                                                            <p className="text-sm text-muted-foreground">
                                                                {propertyOwner.propertiesCount} propriedades cadastradas
                                                            </p>
                                                        </div>

                                                        <div className="flex flex-col gap-2">
                                                            <Link href={`/profile/landlord/${propertyOwner.id}`}>
                                                                <Button variant="outline" size="sm">
                                                                    <User className="h-4 w-4 mr-2" />
                                                                    Ver Perfil
                                                                </Button>
                                                            </Link>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </CardContent>
                                    </Card>

                                    {/* Botão para corretores interessados */}
                                    <Card className="bg-muted/30">
                                        <CardContent className="p-6 text-center">
                                            <h4 className="font-medium mb-2">É corretor e tem interesse neste imóvel?</h4>
                                            <p className="text-sm text-muted-foreground mb-4">
                                                Entre em contato para discutir oportunidades de corretagem
                                            </p>
                                            <Button className="bg-green-600 hover:bg-green-700">
                                                Solicitar Corretagem
                                            </Button>
                                        </CardContent>
                                    </Card>
                                </div>
                            </TabsContent>
                        </Tabs>
                    </div>

                    <div>
                        <Card>
                            <CardContent className="p-6">
                                <h3 className="font-heading text-lg font-medium mb-4">Interessado neste imóvel?</h3>
                                <p className="text-sm text-muted-foreground mb-6">
                                    Preencha o formulário abaixo para agendar uma visita ou
                                    obter mais informações sobre este imóvel.
                                </p>

                                <div className="grid gap-4">
                                    <Button className="bg-[#e56b4e] hover:bg-[#e56b4e]/90 w-full">
                                        Agendar visita
                                    </Button>
                                    <Button variant="outline" className="w-full">
                                        Contatar anunciante
                                    </Button>
                                    <Button variant="ghost" className="w-full flex items-center gap-2">
                                        Compartilhar imóvel
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

// Dados estáticos para demonstração


export default PropertyDetailsPage;
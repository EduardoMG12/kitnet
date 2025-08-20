'use client';

import React from "react";
import MainLayout from "@/components/layout/MainLayout";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
    MapPin,
    Star,
    Building2,
    Phone,
    Mail,
    Calendar,
    Award,
    Users,
    MessageCircle,
    Shield
} from "lucide-react";
import PropertyCard from "@/components/PropertyCard";
import { useParams } from "next/navigation";

const ProfilePage = () => {
    const { id, type } = useParams();

    // Mock data - na implementação real, isso viria de uma API
    const userData = {
        broker: {
            name: "João Silva",
            email: "joao.silva@casaalegria.com",
            phone: "(11) 99999-9999",
            avatar: "/placeholder-avatar.jpg",
            creci: "12345-F",
            company: "Casa Alegria Imóveis",
            rating: 4.8,
            reviewsCount: 127,
            experience: "8 anos",
            specialties: ["Apartamentos", "Casas", "Comercial"],
            description: "Corretor especializado em imóveis residenciais na zona sul do Rio de Janeiro. Mais de 8 anos de experiência no mercado imobiliário.",
            propertiesCount: 24,
            clientsServed: 89,
            location: "Rio de Janeiro, RJ"
        },
        landlord: {
            name: "Maria Santos",
            email: "maria.santos@email.com",
            phone: "(21) 98888-8888",
            avatar: "/placeholder-avatar.jpg",
            rating: 4.5,
            reviewsCount: 23,
            propertiesCount: 6,
            description: "Proprietária com múltiplos imóveis em Copacabana e Ipanema. Sempre disponível para atender inquilinos.",
            location: "Rio de Janeiro, RJ",
            memberSince: "2020"
        },
        tenant: {
            name: "Carlos Oliveira",
            email: "carlos.oliveira@email.com",
            phone: "(11) 97777-7777",
            avatar: "/placeholder-avatar.jpg",
            rating: 4.9,
            reviewsCount: 12,
            description: "Inquilino confiável, sempre em dia com pagamentos. Cuida bem dos imóveis.",
            location: "São Paulo, SP",
            memberSince: "2021",
            propertiesCount: 3
        }
    };

    // Validate userType and get user data
    const userType = type as keyof typeof userData;
    const user = userData[userType];

    // If user doesn't exist, show error message
    if (!user || !userType || !['broker', 'landlord', 'tenant'].includes(userType)) {
        return (
            <MainLayout>
                <div className="container px-4 py-8 mx-auto max-w-7xl">
                    <Card>
                        <CardContent className="p-8 text-center">
                            <h1 className="font-heading text-2xl font-bold mb-4">Usuário não encontrado</h1>
                            <p className="text-muted-foreground">O perfil solicitado não existe ou o tipo de usuário é inválido.</p>
                        </CardContent>
                    </Card>
                </div>
            </MainLayout>
        );
    }

    const mockProperties = [
        {
            id: '1',
            title: 'Apartamento moderno com vista para o mar',
            location: 'Copacabana, Rio de Janeiro',
            price: 2500,
            image: 'https://images.unsplash.com/photo-1483058712412-4245e9b90334?auto=format&fit=crop&w=1200&q=80',
            type: 'Apartamento',
            bedrooms: 2,
            bathrooms: 1,
            area: 75
        },
        {
            id: '2',
            title: 'Casa espaçosa com jardim',
            location: 'Morumbi, São Paulo',
            price: 4200,
            image: 'https://images.unsplash.com/photo-1487958449943-2429e8be8625?auto=format&fit=crop&w=1200&q=80',
            type: 'Casa',
            bedrooms: 4,
            bathrooms: 3,
            area: 180
        }
    ];

    return (
        <MainLayout>
            <div className="container px-4 py-8 mx-auto max-w-7xl">
                {/* Profile Header */}
                <Card className="mb-8">
                    <CardContent className="p-8">
                        <div className="flex flex-col md:flex-row items-start gap-6">
                            <Avatar className="h-24 w-24">
                                <AvatarImage src={user.avatar} alt={user.name} />
                                <AvatarFallback className="text-2xl bg-[#e56b4e] text-white">
                                    {user.name.split(' ').map(n => n[0]).join('')}
                                </AvatarFallback>
                            </Avatar>

                            <div className="flex-1">
                                <div className="flex flex-col md:flex-row md:items-start md:justify-between gap-4">
                                    <div>
                                        <h1 className="font-heading text-3xl font-bold mb-2">{user.name}</h1>
                                        {userType === 'broker' && 'creci' in user && 'company' in user && (
                                            <div className="flex items-center gap-2 mb-2">
                                                <Badge variant="secondary">CRECI: {user.creci}</Badge>
                                                <Badge className="bg-[#e56b4e]">{user.company}</Badge>
                                            </div>
                                        )}
                                        <div className="flex items-center gap-4 text-muted-foreground mb-2">
                                            <div className="flex items-center gap-1">
                                                <MapPin className="h-4 w-4" />
                                                <span>{user.location}</span>
                                            </div>
                                            {userType !== 'broker' && 'memberSince' in user && (
                                                <div className="flex items-center gap-1">
                                                    <Calendar className="h-4 w-4" />
                                                    <span>Membro desde {user.memberSince}</span>
                                                </div>
                                            )}
                                        </div>
                                        <div className="flex items-center gap-1 mb-4">
                                            <Star className="h-5 w-5 fill-yellow-400 text-yellow-400" />
                                            <span className="font-medium">{user.rating}</span>
                                            <span className="text-muted-foreground">({user.reviewsCount} avaliações)</span>
                                        </div>
                                        <p className="text-muted-foreground">{user.description}</p>
                                    </div>

                                    <div className="flex flex-col gap-2">
                                        <Button className="bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                            <MessageCircle className="h-4 w-4 mr-2" />
                                            Enviar Mensagem
                                        </Button>
                                        <Button variant="outline">
                                            <Phone className="h-4 w-4 mr-2" />
                                            Contatar
                                        </Button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </CardContent>
                </Card>

                {/* Stats Cards */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                    <Card>
                        <CardContent className="p-6 text-center">
                            <Building2 className="h-8 w-8 text-[#e56b4e] mx-auto mb-2" />
                            <h3 className="text-2xl font-bold">{user.propertiesCount}</h3>
                            <p className="text-sm text-muted-foreground">
                                {userType === 'tenant' ? 'Imóveis Alugados' : 'Propriedades'}
                            </p>
                        </CardContent>
                    </Card>

                    {userType === 'broker' && 'clientsServed' in user && (
                        <Card>
                            <CardContent className="p-6 text-center">
                                <Users className="h-8 w-8 text-[#e56b4e] mx-auto mb-2" />
                                <h3 className="text-2xl font-bold">{user.clientsServed}</h3>
                                <p className="text-sm text-muted-foreground">Clientes Atendidos</p>
                            </CardContent>
                        </Card>
                    )}

                    <Card>
                        <CardContent className="p-6 text-center">
                            <Award className="h-8 w-8 text-[#e56b4e] mx-auto mb-2" />
                            <h3 className="text-2xl font-bold">{user.rating}</h3>
                            <p className="text-sm text-muted-foreground">Avaliação Média</p>
                        </CardContent>
                    </Card>

                    {userType === 'broker' && 'experience' in user && (
                        <Card>
                            <CardContent className="p-6 text-center">
                                <Shield className="h-8 w-8 text-[#e56b4e] mx-auto mb-2" />
                                <h3 className="text-2xl font-bold">{user.experience}</h3>
                                <p className="text-sm text-muted-foreground">Experiência</p>
                            </CardContent>
                        </Card>
                    )}
                </div>

                {/* Tabs Content */}
                <Tabs defaultValue="properties" className="w-full">
                    <TabsList className="grid w-full grid-cols-3">
                        <TabsTrigger value="properties">
                            {userType === 'tenant' ? 'Histórico' : 'Propriedades'}
                        </TabsTrigger>
                        <TabsTrigger value="reviews">Avaliações</TabsTrigger>
                        <TabsTrigger value="contact">Contato</TabsTrigger>
                    </TabsList>

                    <TabsContent value="properties" className="mt-6">
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {mockProperties.map((property) => (
                                <PropertyCard key={property.id} {...property} />
                            ))}
                        </div>
                    </TabsContent>

                    <TabsContent value="reviews" className="mt-6">
                        <div className="space-y-4">
                            {[1, 2, 3].map((review) => (
                                <Card key={review}>
                                    <CardContent className="p-6">
                                        <div className="flex items-start gap-4">
                                            <Avatar>
                                                <AvatarFallback>U{review}</AvatarFallback>
                                            </Avatar>
                                            <div className="flex-1">
                                                <div className="flex items-center gap-2 mb-2">
                                                    <span className="font-medium">Cliente {review}</span>
                                                    <div className="flex items-center">
                                                        {[...Array(5)].map((_, i) => (
                                                            <Star key={`review-${review}-star-${// biome-ignore lint/suspicious/noArrayIndexKey: <explanation>
                                                                i}`} className="h-4 w-4 fill-yellow-400 text-yellow-400" />
                                                        ))}
                                                    </div>
                                                </div>
                                                <p className="text-muted-foreground">
                                                    Excelente profissional! Muito atencioso e encontrou exatamente o que eu estava procurando.
                                                </p>
                                                <span className="text-xs text-muted-foreground mt-2 block">
                                                    Há 2 semanas
                                                </span>
                                            </div>
                                        </div>
                                    </CardContent>
                                </Card>
                            ))}
                        </div>
                    </TabsContent>

                    <TabsContent value="contact" className="mt-6">
                        <Card>
                            <CardHeader>
                                <CardTitle>Informações de Contato</CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-4">
                                <div className="flex items-center gap-3">
                                    <Mail className="h-5 w-5 text-[#e56b4e]" />
                                    <span>{user.email}</span>
                                </div>
                                <div className="flex items-center gap-3">
                                    <Phone className="h-5 w-5 text-[#e56b4e]" />
                                    <span>{user.phone}</span>
                                </div>
                                <div className="flex items-center gap-3">
                                    <MapPin className="h-5 w-5 text-[#e56b4e]" />
                                    <span>{user.location}</span>
                                </div>
                                {userType === 'broker' && 'company' in user && (
                                    <div className="flex items-center gap-3">
                                        <Building2 className="h-5 w-5 text-[#e56b4e]" />
                                        <span>{user.company}</span>
                                    </div>
                                )}
                                {userType === 'broker' && 'specialties' in user && (
                                    <div className="pt-4">
                                        <h4 className="font-medium mb-2">Especialidades:</h4>
                                        <div className="flex flex-wrap gap-2">
                                            {user.specialties.map((specialty) => (
                                                <Badge key={specialty} variant="secondary">{specialty}</Badge>
                                            ))}
                                        </div>
                                    </div>
                                )}
                            </CardContent>
                        </Card>
                    </TabsContent>
                </Tabs>
            </div>
        </MainLayout>
    );
};

export default ProfilePage;
import MainLayout from "@/components/layout/MainLayout";
import AccessDeniedInteractiveContent from '@/components/errors/AccessDeniedInteractiveContent';
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "403 - Acesso Negado | Kitnet",
    description: "Acesso proibido! Você não tem permissão para acessar esta página. Tente fazer login com uma conta autorizada.",
    // Next.js gerencia `canonical` automaticamente se você definir a base URL corretamente.
    // Para noindex/nofollow, usamos `robots`.
    robots: {
        index: false,
        follow: false,
        nocache: true, // Adiciona uma diretiva de no-cache para garantir que não seja indexado
        googleBot: {
            index: false,
            follow: false,
            noimageindex: true,
            'max-video-preview': -1,
            'max-snippet': -1,
        },
    },
    openGraph: {
        url: 'https://www.kitnet.com.br/errors/access-denied',
        title: '403 - Acesso Negado | Kitnet',
        description: 'Acesso proibido! Você não tem permissão para acessar esta página.',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-access-denied.png',
                width: 800,
                height: 600,
                alt: 'Kitnet - Acesso Negado',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '403 - Acesso Negado | Kitnet',
        description: 'Acesso proibido! Você não tem permissão para acessar esta página.',
        creator: '@KitnetBR', // Seu handle do Twitter
        site: '@KitnetBR',   // O handle do site/aplicação
        images: ['https://www.kitnet.com.br/images/kitnet-logo-access-denied.png'],
    },
    // Você pode adicionar outros metadados aqui, se precisar
    // Por exemplo: icons, viewport, themeColor etc.
};


export default function AccessDeniedPage() {
    return (
        <MainLayout hideFooter>
            <AccessDeniedInteractiveContent />
        </MainLayout>
    );
}
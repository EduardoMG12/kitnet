import MainLayout from "@/components/layout/MainLayout";
import NotFoundInteractive from '@/components/errors/NotFoundInteractive';
import type { Metadata } from 'next';

export const metadata: Metadata = {
    title: "404 - Página Não Encontrada | Kitnet",
    description: "Ops! Parece que você se perdeu. A página que você procurava não foi encontrada na Kitnet. Volte para a página inicial e explore nossos imóveis.",
    robots: {
        index: false,
        follow: false,
    },
    openGraph: {
        url: 'https://www.kitnet.com.br/404',
        title: '404 - Página Não Encontrada',
        description: 'Esta página não existe na Kitnet. Encontre seu novo lar conosco!',
        siteName: 'Kitnet',
        type: 'website',
        images: [
            {
                url: 'https://www.kitnet.com.br/images/kitnet-logo-error.png',
                width: 800,
                height: 600,
                alt: 'Kitnet - Página Não Encontrada',
            },
        ],
    },
    twitter: {
        card: 'summary_large_image',
        title: '404 - Página Não Encontrada',
        description: 'Esta página não existe na Kitnet. Encontre seu novo lar conosco!',
        creator: '@KitnetBR', // O handle do seu Twitter
        site: '@KitnetBR', // O handle do site/app
        images: ['https://www.kitnet.com.br/images/kitnet-logo-error.png'],
    },
    // canonical: 'https://www.kitnet.com.br/404' // Next.js geralmente cuida de canonicals se você definir o URL base no layout principal
};


export default function NotFoundPage() {
    return (
        <MainLayout hideFooter>
            <NotFoundInteractive />
        </MainLayout>
    );
}
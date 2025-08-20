'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { useEffect, useState } from 'react';

import { ArrowLeft, Frown, HomeIcon, MehIcon, RotateCcw } from 'lucide-react';
import { Card } from '@/components/ui/card';
import { Button } from '@/components/ui/button';

const NotFoundInteractive = () => {
    const pathname = usePathname();
    const [searchTerm, setSearchTerm] = useState<string>("");
    const [emotions, setEmotions] = useState<number>(0);

    useEffect(() => {
        const pathParts = pathname.split("/");
        if (pathParts.length > 1) {

            setSearchTerm(pathParts[pathParts.length - 1].replace(/-|_/g, " "));
        }


        const interval = setInterval(() => {
            setEmotions((prev) => (prev < 2 ? prev + 1 : 0));
        }, 2000);


        return () => clearInterval(interval);
    }, [pathname]);


    const getEmotionIcon = () => {
        switch (emotions) {
            case 0:
                return <Frown className="h-20 w-20 text-purple-500 animate-pulse" />;
            case 1:
                return <MehIcon className="h-20 w-20 text-amber-500 animate-bounce" />;
            default:
                return (
                    <div className="relative">
                        <Frown className="h-20 w-20 text-purple-500 animate-spin" />
                        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-2xl">
                            ?
                        </div>
                    </div>
                );
        }
    };


    const reloadPage = () => {
        window.location.reload();
    };

    return (
        <div className="h-screen flex flex-col items-center justify-center bg-gradient-to-b from-purple-50 to-purple-100 dark:from-gray-900 dark:to-gray-800 p-4">
            <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-purple-200 dark:border-gray-700">
                <div className="flex flex-col items-center text-center">
                    <div className="mb-4">
                        {getEmotionIcon()}
                    </div>

                    <h1 className="text-6xl font-bold mb-2 text-purple-600 dark:text-purple-400">404</h1>
                    <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                        Ops! Página não encontrada
                    </h2>

                    <p className="text-gray-600 dark:text-gray-400 mb-6">
                        Parece que você se perdeu no caminho.
                        {searchTerm && (
                            <span className="block mt-2">
                                Não conseguimos encontrar nada relacionado a
                                <span className="font-semibold text-purple-500"> "{searchTerm}"</span>.
                            </span>
                        )}
                    </p>

                    <div className="space-y-4 w-full">
                        <div className="p-4 bg-purple-50 dark:bg-gray-700 rounded-lg">
                            <p className="text-sm text-gray-600 dark:text-gray-300">
                                Enquanto você está aqui, que tal tentar uma das nossas páginas populares:
                            </p>
                            <div className="mt-3 flex flex-wrap gap-2 justify-center">
                                <Link href="/">
                                    <Button variant="outline" size="sm" className="bg-white dark:bg-gray-800">
                                        <HomeIcon className="h-4 w-4 mr-2" />
                                        Página inicial
                                    </Button>
                                </Link>
                                <Link href="/properties">
                                    <Button variant="outline" size="sm" className="bg-white dark:bg-gray-800">
                                        Imóveis
                                    </Button>
                                </Link>
                                <Link href="/add-property">
                                    <Button variant="outline" size="sm" className="bg-white dark:bg-gray-800">
                                        Anunciar
                                    </Button>
                                </Link>
                            </div>
                        </div>

                        <div className="flex flex-col sm:flex-row gap-3 justify-center">
                            <Button
                                onClick={() => window.history.back()}
                                variant="outline"
                                className="flex-1"
                            >
                                <ArrowLeft className="h-4 w-4 mr-2" />
                                Voltar
                            </Button>
                            <Button
                                onClick={reloadPage}
                                variant="ghost"
                                className="flex-1"
                            >
                                <RotateCcw className="h-4 w-4 mr-2" />
                                Tentar novamente
                            </Button>
                            <Link href="/" className="flex-1">
                                <Button className="w-full bg-[#e56b4e] hover:bg-[#e56b4e]/90">
                                    <HomeIcon className="h-4 w-4 mr-2" />
                                    Início
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </Card>

            <div className="mt-10 max-w-lg text-center">
                <p className="text-gray-600 dark:text-gray-400 text-sm">
                    Se você acredita que isso é um erro, por favor entre em contato com nossa equipe de suporte.
                </p>
            </div>
        </div>
    );
};

export default NotFoundInteractive; 
import Footer from "@/components/Footer";
import Header from "@/components/headers";


export default function LayoutWithFooter({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <Header />
      {children}
      <Footer />
    </>
  );
}